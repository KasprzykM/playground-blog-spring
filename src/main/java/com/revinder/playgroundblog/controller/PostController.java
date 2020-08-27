package com.revinder.playgroundblog.controller;

import com.revinder.playgroundblog.model.Post;
import com.revinder.playgroundblog.model.dto.PostDTO;
import com.revinder.playgroundblog.service.PostService;
import com.revinder.playgroundblog.util.modelassemblers.PostModelAssembler;
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
@RequestMapping(value = "/api/posts")
public class PostController {

    private final PostService postService;

    private final PostModelAssembler postModelAssembler;

    @Autowired
    public PostController(PostService postService, PostModelAssembler postModelAssembler)
    {
        this.postService = postService;
        this.postModelAssembler = postModelAssembler;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CollectionModel<EntityModel<PostDTO>>> findAll()
    {
        List<EntityModel<PostDTO>> posts = postService.findAll().stream()
                .map(postModelAssembler::toModel).collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(posts,
                        linkTo(methodOn(PostController.class).findAll()).withSelfRel()));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EntityModel<PostDTO>> create(@RequestBody Post post)
    {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Post newPost = postService.save(post, username);
        EntityModel<PostDTO> postResource = postModelAssembler.toModel(newPost);
        return ResponseEntity
                .created(postResource.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(postResource);
    }

    @PutMapping("/{postId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EntityModel<PostDTO>> updateByPostId(@RequestBody Post post,
                                                               @PathVariable Long postId)
    {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Post updatedPost = postService.updatePost(post, postId, username);
        EntityModel<PostDTO> postResource = postModelAssembler.toModel(updatedPost);
        return ResponseEntity
                .created(postResource.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(postResource);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteById(@PathVariable Long id)
    {
        postService.deleteById(id);
        return ResponseEntity.noContent()
                .build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public EntityModel<PostDTO> findById(@PathVariable Long id)
    {
        return postModelAssembler.toModel(postService.findById(id));
    }

    @GetMapping("/byUser/{username}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CollectionModel<EntityModel<PostDTO>>> findByLogin(@PathVariable String username)
    {
        List<EntityModel<PostDTO>> posts = postService.findByUserName(username)
                .stream().map(postModelAssembler::toModel).collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(posts,
                        linkTo(methodOn(PostController.class).findAll()).withSelfRel()));
    }

}
