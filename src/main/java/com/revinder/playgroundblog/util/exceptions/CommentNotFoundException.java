package com.revinder.playgroundblog.util.exceptions;

public class CommentNotFoundException extends ResourceNotFoundException {
    public CommentNotFoundException(Long id) {
        super("Unable to find comment with id of " + id + ".");
    }


    public CommentNotFoundException(String username)
    {
        super("Unable to find any comments made by " + username + ".");
    }
}
