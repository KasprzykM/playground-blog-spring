package com.revinder.playgroundblog.controller;

import com.revinder.playgroundblog.model.User;
import com.revinder.playgroundblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public @ResponseBody
    ResponseEntity<Iterable<User>> findAll()
    {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.findAll());
    }

    @PostMapping
    public @ResponseBody ResponseEntity<User> create(@RequestBody User user)
    {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.save(user));
    }

    @PutMapping("/{id}")
    public @ResponseBody ResponseEntity<User> updateById(@RequestBody User user)
    {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.save(user));
    }


    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<User> findById(@PathVariable Long id)
    {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.findById(id));
    }

    @GetMapping("/name/{userLogin}")
    public @ResponseBody ResponseEntity<User> findByLogin(@PathVariable String userLogin)
    {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.findByLogin(userLogin));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id)
    {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
