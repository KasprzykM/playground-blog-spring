package com.revinder.playgroundblog.model.dto;

import com.revinder.playgroundblog.model.Post;
import lombok.Data;

import java.util.Date;

@Data
public class PostDTO {

    private UserDTO user;
    private String title;
    private String rawContent;
    private String renderedContent;
    private Date createdAt;
    private Date lastUpdatedAt;
    private boolean draft;

    public PostDTO(Post post)
    {
        this.user            = new UserDTO(post.getUser());
        this.title           = post.getTitle();
        this.rawContent      = post.getRawContent();
        this.renderedContent = post.getRenderedContent();
        this.createdAt       = post.getCreatedAt();
        this.lastUpdatedAt   = post.getLastUpdatedAt();
        this.draft           = post.isDraft();
    }

}
