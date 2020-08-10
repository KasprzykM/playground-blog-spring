package com.revinder.playgroundblog.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Unable to find user.")
public class UserNotFoundException extends ResourceNotFoundException{

    public UserNotFoundException(Long id) {
        super("Unable to find user with id of " + id + ".");
    }

    public UserNotFoundException(String login)
    {
        super("Unable to find user with login of " + login + ".");
    }
}
