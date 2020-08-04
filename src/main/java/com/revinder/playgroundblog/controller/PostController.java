package com.revinder.playgroundblog.controller;

import com.revinder.playgroundblog.model.Post;
import com.revinder.playgroundblog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService)
    {
        this.postService = postService;
    }

    @GetMapping
    public @ResponseBody
    ResponseEntity<Iterable<Post>> findAll()
    {
        return ResponseEntity.status(HttpStatus.OK)
                .body(postService.findAll());
    }

    @PostMapping
    public @ResponseBody ResponseEntity<Post> create(@RequestBody Post post)
    {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postService.save(post));
    }

    @PutMapping("/{id}")
    public @ResponseBody ResponseEntity<Post> updateById(@RequestBody Post post)
    {
        return ResponseEntity.status(HttpStatus.OK)
                .body(postService.save(post));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id)
    {
        postService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<Post> findById(@PathVariable Long id)
    {
        return ResponseEntity.status(HttpStatus.OK)
                .body(postService.findById(id));
    }

    @GetMapping("/byUser/{login}")
    public @ResponseBody ResponseEntity<Iterable<Post>> findByLogin(@PathVariable String login)
    {
        return ResponseEntity.status(HttpStatus.OK)
                .body(postService.findByUserLogin(login));
    }

}
