package com.revinder.playgroundblog.util.modelassemblers;

import com.revinder.playgroundblog.controller.UserController;
import com.revinder.playgroundblog.model.Post;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PostModelAssembler implements RepresentationModelAssembler<Post, EntityModel<Post>> {

    @Override
    public EntityModel<Post> toModel(Post entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(UserController.class).findById(entity.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).findAll()).withRel("posts"));
    }
}
