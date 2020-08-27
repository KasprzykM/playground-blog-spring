package com.revinder.playgroundblog.model.dto;

import com.revinder.playgroundblog.model.User;
import lombok.Data;
import org.springframework.hateoas.server.core.Relation;

@Data
@Relation(collectionRelation = "users")
public class UserDTO {

    private String email;
    private String username;


    public UserDTO(User user)
    {
        this.email    = user.getEmail();
        this.username = user.getUsername();
    }
}
