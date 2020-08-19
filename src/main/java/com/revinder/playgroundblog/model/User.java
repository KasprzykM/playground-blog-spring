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
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(length = 5)
    private Role role;

    public void updateFrom(User user)
    {
        this.username = user.username    != null ? user.username : this.username;
        this.password = user.password    != null ? user.password : this.password;
        this.email    = user.email       != null ? user.email    : this.email;
    }


    public enum Role {

        USER("USER"),
        ADMIN("ADMIN");

        Role(String role) {
        }
    }

}
