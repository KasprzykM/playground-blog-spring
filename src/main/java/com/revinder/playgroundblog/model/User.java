package com.revinder.playgroundblog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;


    public void replaceFrom(User user)
    {
        this.login    = user.login       != null ? user.login    : this.login;
        this.password = user.password    != null ? user.password : this.password;
        this.email    = user.email       != null ? user.email    : this.email;
    }

}
