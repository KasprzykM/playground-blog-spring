package com.revinder.playgroundblog.controller;

import com.revinder.playgroundblog.model.Post;
import com.revinder.playgroundblog.model.User;
import com.revinder.playgroundblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<User>>> findAll() {
        List<EntityModel<User>> users = userService.findAll().stream()
                .map(user -> EntityModel.of(user,
                        linkTo(methodOn(UserController.class).findById(user.getId())).withSelfRel(),
                        linkTo(methodOn(UserController.class).findAll()).withRel("users")
                )).collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(users,
                        linkTo(methodOn(UserController.class).findAll()).withSelfRel()));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody User user) {
        try {
            User savedUser = userService.save(user);
            EntityModel<User> userResource = EntityModel.of(savedUser,
                    linkTo(methodOn(UserController.class).findById(user.getId())).withSelfRel());
            return ResponseEntity.created(new URI(userResource.getRequiredLink(IanaLinkRelations.SELF).getHref()))
                    .body(user);
        } catch (URISyntaxException e) {
            return ResponseEntity.badRequest().body("Unable to create user " + user);
        }
    }

    @PutMapping("/{id}")
    public @ResponseBody
    ResponseEntity<User> updateById(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.save(user));
    }


    @GetMapping("/{id}")
    public @ResponseBody
    ResponseEntity<User> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.findById(id));
    }

    @GetMapping("/name/{userLogin}")
    public @ResponseBody
    ResponseEntity<User> findByLogin(@PathVariable String userLogin) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.findByLogin(userLogin));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
