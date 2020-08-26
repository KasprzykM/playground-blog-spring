package com.revinder.playgroundblog.controller;


import com.revinder.playgroundblog.model.Comment;
import com.revinder.playgroundblog.model.Post;
import com.revinder.playgroundblog.service.CommentService;
import com.revinder.playgroundblog.util.modelassemblers.CommentModelAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public EntityModel<Comment> findById(@PathVariable Long id)
    {
        return commentModelAssembler.toModel(commentService.findById(id));
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CollectionModel<EntityModel<Comment>>> findAll()
    {
        List<EntityModel<Comment>> comments = commentService.findAll().stream()
                .map(commentModelAssembler::toModel).collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(comments,
                        linkTo(methodOn(CommentController.class).findAll()).withSelfRel()));
    }
}
