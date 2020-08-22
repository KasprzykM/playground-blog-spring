package com.revinder.playgroundblog.util.exceptions;

public class UserNotFoundException extends ResourceNotFoundException{

    public UserNotFoundException(Long id) {
        super("Unable to find user with id of " + id + ".");
    }

    public UserNotFoundException(String login)
    {
        super("Unable to find user with login of " + login + ".");
    }
}
