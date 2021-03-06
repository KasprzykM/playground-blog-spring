package com.revinder.playgroundblog.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private Date createdAt = new Date();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User user;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Post post;


    public void updateFrom(Comment comment)
    {
        this.post = comment.post != null ? comment.post : this.post;
        this.user = comment.user != null ? comment.user : this.user;
        this.text = comment.text != null ? comment.text : this.text;
    }
}
