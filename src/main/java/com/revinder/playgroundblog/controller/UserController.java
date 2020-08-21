package com.revinder.playgroundblog.controller;

import com.revinder.playgroundblog.model.User;
import com.revinder.playgroundblog.service.UserService;
import com.revinder.playgroundblog.util.UserMismatchException;
import com.revinder.playgroundblog.util.modelassemblers.UserModelAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CollectionModel<EntityModel<User>>> findAll() {

        List<EntityModel<User>> users = userService.findAll()
                .stream().map(userModelAssembler::toModel).collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(users,
                        linkTo(methodOn(UserController.class).findAll()).withSelfRel()));
    }

    @PostMapping("/register")
    public ResponseEntity<EntityModel<User>> register(@RequestBody User user) {
        User newUser = userService.save(user);
        EntityModel<User> userResource = userModelAssembler.toModel(newUser);
        return ResponseEntity
                .created(userResource.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(userResource);
    }

    @PutMapping("/updateUser")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<EntityModel<User>> changePassword(@RequestBody User newUserDetails) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String loggedInUser = ((UserDetails) principal).getUsername();
            if (loggedInUser.equals(newUserDetails.getUsername())) {
                User userToUpdate = userService.findByUsername(newUserDetails.getUsername());
                User updatedUser = userService.update(newUserDetails, userToUpdate.getId());
                EntityModel<User> userResource = userModelAssembler.toModel(updatedUser);
                return ResponseEntity
                        .created(userResource.getRequiredLink(IanaLinkRelations.SELF).toUri())
                        .body(userResource);
            }
        }
        throw new UserMismatchException("Incorrect login.");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EntityModel<User>> updateById(@RequestBody User user,
                                                        @PathVariable Long id) {
        User updatedUser = userService.update(user, id);
        EntityModel<User> userResource = userModelAssembler.toModel(updatedUser);
        return ResponseEntity
                .created(userResource.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(userResource);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public EntityModel<User> findById(@PathVariable Long id) {
        return userModelAssembler.toModel(userService.findById(id));
    }

    @GetMapping("/name/{username}")
    @PreAuthorize("hasRole('USER')")
    public EntityModel<User> findByUsername(@PathVariable String username) {
        return userModelAssembler.toModel(userService.findByUsername(username));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
