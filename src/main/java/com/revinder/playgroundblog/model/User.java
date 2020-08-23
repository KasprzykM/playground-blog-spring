package com.revinder.playgroundblog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Builder
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

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public void updateFrom(User user)
    {
        this.username = user.username    != null ? user.username : this.username;
        this.password = user.password    != null ? user.password : this.password;
        this.email    = user.isEmailValid()      ? user.email    : this.email;
    }

    public boolean isEmailValid()
    {
        return EmailValidator.getInstance().isValid(this.email);
    }


    public enum Role {

        USER("USER"),
        ADMIN("ADMIN");

        Role(String role) {
        }
    }

}
