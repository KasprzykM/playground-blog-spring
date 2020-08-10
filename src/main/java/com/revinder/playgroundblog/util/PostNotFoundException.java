package com.revinder.playgroundblog.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Unable to find post.")
public class PostNotFoundException extends ResourceNotFoundException{

    public PostNotFoundException(Long id) {
        super("Unable to find post with id of " + id);
    }

    public PostNotFoundException(String login)
    {
        super("Unable to find post written by user with login " + login + ".");
    }
}
