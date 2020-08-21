package com.revinder.playgroundblog.util.modelassemblers;

import com.revinder.playgroundblog.controller.UserController;
import com.revinder.playgroundblog.model.User;
import com.revinder.playgroundblog.model.UserDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<User, EntityModel<UserDTO>> {

    @Override
    public EntityModel<UserDTO> toModel(User entity) {
        return EntityModel.of(toUserDTO(entity),
                linkTo(methodOn(UserController.class).findById(entity.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).findAll()).withRel("users"));
    }

    private UserDTO toUserDTO(User userEntity)
    {
        return new UserDTO(userEntity);
    }
}
