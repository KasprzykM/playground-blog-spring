package com.revinder.playgroundblog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Post {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(cascade = CascadeType.PERSIST, optional = false)
    @JoinColumn(nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;


    @Column(nullable = false)
    @Type(type = "text")
    private String rawContent;


    @Column(nullable = false)
    @Type(type = "text")
    private String renderedContent;


    @Column(nullable = false)
    @Type(type = "text")
    private String renderedSummary;

    @Column(nullable = false)
    private Date createdAt = new Date();

    @Column(nullable = false)
    private Date lastUpdatedAt = new Date();

    @Column(nullable = false)
    private boolean draft = true;


    public void updateFrom(Post post)
    {
        this.draft           = post.draft;
        this.renderedSummary = post.renderedSummary != null ? post.renderedSummary : this.renderedSummary;
        this.renderedContent = post.renderedContent != null ? post.renderedContent : this.renderedContent;
        this.rawContent      = post.title           != null ? post.title           : this.title;

        this.lastUpdatedAt = new Date();
    }
}
