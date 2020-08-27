package com.revinder.playgroundblog.controller;

import com.revinder.playgroundblog.model.Comment;
import com.revinder.playgroundblog.model.dto.CommentDTO;
import com.revinder.playgroundblog.service.CommentService;
import com.revinder.playgroundblog.util.exceptions.UserMismatchException;
import com.revinder.playgroundblog.util.modelassemblers.CommentModelAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/comments")
public class CommentController {

    private final CommentService commentService;
    private final CommentModelAssembler commentModelAssembler;

    @Autowired
    public CommentController(CommentService commentService, CommentModelAssembler commentModelAssembler) {
        this.commentService = commentService;
        this.commentModelAssembler = commentModelAssembler;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public EntityModel<CommentDTO> findById(@PathVariable Long id) {
        return commentModelAssembler.toModel(commentService.findById(id));
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CollectionModel<EntityModel<CommentDTO>>> findAll() {
        return toResponseEntity(commentService.findAll());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        commentService.deleteById(id);
        return ResponseEntity.noContent()
                .build();
    }

    @PostMapping("/{postId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<EntityModel<CommentDTO>> create(@RequestBody Comment comment,
                                                          @PathVariable Long postId) {
        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Comment newComment = commentService.save(comment, loggedInUser, postId);
        return toResponseEntity(newComment);
    }

    @GetMapping("/{postId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CollectionModel<EntityModel<CommentDTO>>> findAllByPostId(@PathVariable Long postId) {
        return toResponseEntity(commentService.findByPost(postId));
    }

    @GetMapping("/byUser")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CollectionModel<EntityModel<CommentDTO>>> findAllByUser(@RequestParam String username) {
        return toResponseEntity(commentService.findByUserName(username));
    }


    @PutMapping("/{commentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EntityModel<CommentDTO>> updateComment(@RequestBody Comment newComment,
                                                                 @PathVariable Long commentId) {
        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Comment updatedComment = commentService.update(newComment, commentId, loggedInUser);
        return toResponseEntity(updatedComment);
    }

    private ResponseEntity<CollectionModel<EntityModel<CommentDTO>>> toResponseEntity(List<Comment> comments)
    {
        List<EntityModel<CommentDTO>> commentsDTO = comments.stream()
                .map(commentModelAssembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(commentsDTO,
                        linkTo(methodOn(CommentController.class).findAll()).withSelfRel()));
    }

    private ResponseEntity<EntityModel<CommentDTO>> toResponseEntity(Comment comment)
    {
        EntityModel<CommentDTO> commentResource = commentModelAssembler.toModel(comment);
        return ResponseEntity
                .created(commentResource.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(commentResource);
    }
}
