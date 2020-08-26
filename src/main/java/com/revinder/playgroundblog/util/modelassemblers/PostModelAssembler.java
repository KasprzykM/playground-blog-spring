package com.revinder.playgroundblog.util.modelassemblers;

import com.revinder.playgroundblog.controller.PostController;
import com.revinder.playgroundblog.controller.UserController;
import com.revinder.playgroundblog.model.Post;
import com.revinder.playgroundblog.model.dto.PostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PostModelAssembler implements RepresentationModelAssembler<Post, EntityModel<PostDTO>> {

    private final UserModelAssembler userModelAssembler;

    @Autowired
    public PostModelAssembler(UserModelAssembler userModelAssembler) {
        this.userModelAssembler = userModelAssembler;
    }

    @Override
    public EntityModel<PostDTO> toModel(Post entity) {
        return EntityModel.of(new PostDTO(entity),
                linkTo(methodOn(UserController.class).findById(entity.getUser().getId())).withSelfRel(),
                linkTo(methodOn(PostController.class).findById(entity.getId())).withSelfRel(),
                linkTo(methodOn(PostController.class).findAll()).withRel("posts"));
    }
}
