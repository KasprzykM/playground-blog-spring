package com.revinder.playgroundblog.model.dto;

import com.revinder.playgroundblog.model.Comment;
import lombok.Data;
import org.springframework.hateoas.server.core.Relation;

import java.util.Date;

@Data
@Relation(collectionRelation = "comments")
public class CommentDTO {

    private String text;
    private Date createdAt;
    private UserDTO commentAuthor;
    private PostDTO commentedPost;


    public CommentDTO(Comment comment)
    {
        this.text          = comment.getText();
        this.createdAt     = comment.getCreatedAt();
        this.commentAuthor = new UserDTO(comment.getUser());
        this.commentedPost = new PostDTO(comment.getPost());
    }
}
