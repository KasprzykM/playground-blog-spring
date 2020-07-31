package com.revinder.playgroundblog.controller;

import com.revinder.playgroundblog.model.User;
import com.revinder.playgroundblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> findAll()
    {
        return userService.findAll();
    }

    @PostMapping
    public User create(@RequestBody User user)
    {
        return userService.save(user);
    }

    @PutMapping("/{id}")
    public User updateById(@RequestBody User user)
    {
        return userService.save(user);
    }


    @GetMapping("/{id}")
    public User findById(@PathVariable String id)
    {
        return userService.findById(id);
    }

    @GetMapping("/name/{login}")
    public User findByLogin(@PathVariable String login)
    {
        return userService.findByLogin(login);
    }


    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id)
    {
        userService.deleteById(id);
    }
}
