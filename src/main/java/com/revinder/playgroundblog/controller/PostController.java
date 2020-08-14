package com.revinder.playgroundblog.controller;

import com.revinder.playgroundblog.model.Post;
import com.revinder.playgroundblog.service.PostService;
import com.revinder.playgroundblog.util.modelassemblers.PostModelAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CollectionModel<EntityModel<Post>>> findAll()
    {
        List<EntityModel<Post>> posts = postService.findAll().stream()
                .map(postModelAssembler::toModel).collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(posts,
                        linkTo(methodOn(PostController.class).findAll()).withSelfRel()));
    }

    @PostMapping("/{userLogin}")
    public ResponseEntity<EntityModel<Post>> create(@RequestBody Post post,
                                                    @PathVariable String userLogin)
    {
        Post newPost = postService.save(post, userLogin);
        EntityModel<Post> postResource = postModelAssembler.toModel(newPost);
        return ResponseEntity
                .created(postResource.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(postResource);
    }

    @PutMapping("/{userLogin}/{postId}")
    public ResponseEntity<EntityModel<Post>> updateByUserLogin(@RequestBody Post post,
                                                               @PathVariable String userLogin,
                                                               @PathVariable Long postId)
    {
        Post updatedPost = postService.updatePost(post, postId, userLogin);
        EntityModel<Post> postResource = postModelAssembler.toModel(updatedPost);
        return ResponseEntity
                .created(postResource.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(postResource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id)
    {
        postService.deleteById(id);
        return ResponseEntity.noContent()
                .build();
    }

    @GetMapping("/{id}")
    public EntityModel<Post> findById(@PathVariable Long id)
    {
        return postModelAssembler.toModel(postService.findById(id));
    }

    @GetMapping("/byUser/{username}")
    public ResponseEntity<CollectionModel<EntityModel<Post>>> findByLogin(@PathVariable String username)
    {
        List<EntityModel<Post>> posts = postService.findByUserLogin(username)
                .stream().map(postModelAssembler::toModel).collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(posts,
                        linkTo(methodOn(PostController.class).findAll()).withSelfRel()));
    }

}
