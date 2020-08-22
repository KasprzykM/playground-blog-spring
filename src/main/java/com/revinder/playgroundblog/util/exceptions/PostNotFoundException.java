package com.revinder.playgroundblog.util.exceptions;

public class PostNotFoundException extends ResourceNotFoundException{

    public PostNotFoundException(Long id) {
        super("Unable to find post with id of " + id);
    }

    public PostNotFoundException(String login)
    {
        super("Unable to find post written by user with login " + login + ".");
    }
}
