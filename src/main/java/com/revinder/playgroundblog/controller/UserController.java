package com.revinder.playgroundblog.controller;

import com.revinder.playgroundblog.model.User;
import com.revinder.playgroundblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public @ResponseBody Iterable<User> findAll()
    {
        return userService.findAll();
    }

    @PostMapping
    public @ResponseBody User create(@RequestBody User user)
    {
        return userService.save(user);
    }

    @PutMapping("/{id}")
    public @ResponseBody User updateById(@RequestBody User user)
    {
        return userService.save(user);
    }


    @GetMapping("/{id}")
    public @ResponseBody User findById(@PathVariable Integer id)
    {
        return userService.findById(id);
    }

    @GetMapping("/name/{login}")
    public @ResponseBody User findByLogin(@PathVariable String login)
    {
        return userService.findByLogin(login);
    }


    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id)
    {
        userService.deleteById(id);
    }
}
