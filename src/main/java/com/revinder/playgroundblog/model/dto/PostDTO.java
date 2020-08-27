package com.revinder.playgroundblog.model.dto;

import com.revinder.playgroundblog.model.Post;
import lombok.Data;
import org.springframework.hateoas.server.core.Relation;

import java.util.Date;

@Data
@Relation(collectionRelation = "posts")
public class PostDTO {

    private UserDTO author;
    private String title;
    private String rawContent;
    private String renderedContent;
    private Date createdAt;
    private Date lastUpdatedAt;
    private boolean draft;

    public PostDTO(Post post)
    {
        this.author          = new UserDTO(post.getUser());
        this.title           = post.getTitle();
        this.rawContent      = post.getRawContent();
        this.renderedContent = post.getRenderedContent();
        this.createdAt       = post.getCreatedAt();
        this.lastUpdatedAt   = post.getLastUpdatedAt();
        this.draft           = post.isDraft();
    }

}
