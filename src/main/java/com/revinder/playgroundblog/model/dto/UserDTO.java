package com.revinder.playgroundblog.model.dto;

import com.revinder.playgroundblog.model.User;
import lombok.Data;

@Data
public class UserDTO {

    private String email;
    private String username;


    public UserDTO(User user)
    {
        this.email    = user.getEmail();
        this.username = user.getUsername();
    }
}
