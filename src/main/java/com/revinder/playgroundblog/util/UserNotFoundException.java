package com.revinder.playgroundblog.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "unable to find user")
public class UserNotFoundException extends ResourceNotFoundException{

    public UserNotFoundException(String id) {
        super("Unable to find user with id of " + id);
    }
}
