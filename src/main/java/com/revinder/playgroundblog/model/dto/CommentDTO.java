package com.revinder.playgroundblog.model.dto;

import com.revinder.playgroundblog.model.Comment;
import lombok.Data;

import java.util.Date;

@Data
public class CommentDTO {

    private String text;
    private Date createdAt;
    private UserDTO userDTO;
    private PostDTO postDTO;


    public CommentDTO(Comment comment)
    {
        this.text      = comment.getText();
        this.createdAt = comment.getCreatedAt();
        this.userDTO   = new UserDTO(comment.getUser());
        this.postDTO   = new PostDTO(comment.getPost());
    }
}
