package com.revinder.playgroundblog.util;

public class PostNotFoundException extends ResourceNotFoundException{

    public PostNotFoundException(String id) {
        super("Unable to find post with id of " + id);
    }
}
