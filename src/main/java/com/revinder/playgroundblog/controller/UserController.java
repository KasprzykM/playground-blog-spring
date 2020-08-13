package com.revinder.playgroundblog.controller;

import com.revinder.playgroundblog.model.User;
import com.revinder.playgroundblog.service.UserService;
import com.revinder.playgroundblog.util.modelassemblers.UserModelAssembler;
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
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    private final UserModelAssembler userModelAssembler;

    @Autowired
    public UserController(UserService userService, UserModelAssembler userModelAssembler) {
        this.userService = userService;
        this.userModelAssembler = userModelAssembler;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<User>>> findAll() {

        List<EntityModel<User>> users = userService.findAll()
                .stream().map(userModelAssembler::toModel).collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(users,
                        linkTo(methodOn(UserController.class).findAll()).withSelfRel()));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody User user) {
        User newUser = userService.save(user);
        EntityModel<User> userResource = userModelAssembler.toModel(newUser);
        return ResponseEntity
                .created(userResource.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(userResource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@RequestBody User user, @PathVariable Long id) {
        User updatedUser = userService.findById(id);
        updatedUser.replaceFrom(user);
        EntityModel<User> userResource = userModelAssembler.toModel(updatedUser);
        return ResponseEntity
                .created(userResource.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(userResource);
    }


    @GetMapping("/{id}")
    public EntityModel<User> findById(@PathVariable Long id) {
        return userModelAssembler.toModel(userService.findById(id));
    }

    @GetMapping("/name/{userLogin}")
    public EntityModel<User> findByLogin(@PathVariable String userLogin) {
        return userModelAssembler.toModel(userService.findByLogin(userLogin));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
