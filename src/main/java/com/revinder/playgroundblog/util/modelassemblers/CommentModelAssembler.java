package com.revinder.playgroundblog.util.modelassemblers;

import com.revinder.playgroundblog.controller.CommentController;
import com.revinder.playgroundblog.controller.PostController;
import com.revinder.playgroundblog.controller.UserController;
import com.revinder.playgroundblog.model.Comment;
import com.revinder.playgroundblog.model.dto.CommentDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CommentModelAssembler implements RepresentationModelAssembler<Comment, EntityModel<CommentDTO>> {


    @Override
    public EntityModel<CommentDTO> toModel(Comment entity) {

        return EntityModel.of(new CommentDTO(entity),
                linkTo(methodOn(UserController.class).findById(entity.getUser().getId())).withSelfRel(),
                linkTo(methodOn(PostController.class).findById(entity.getPost().getId())).withSelfRel(),
                linkTo(methodOn(CommentController.class).findById(entity.getId())).withSelfRel(),
                linkTo(methodOn(CommentController.class).findAll()).withRel("comments"));
    }
}
