package com.revinder.playgroundblog.util;

public class UserNotFoundException extends ResourceNotFoundException{


    public UserNotFoundException(String id) {
        super("Unable to find user with id of " + id);
    }
}
