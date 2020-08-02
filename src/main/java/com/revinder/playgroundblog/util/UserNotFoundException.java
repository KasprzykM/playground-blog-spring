package com.revinder.playgroundblog.util;

public class UserNotFoundException extends ResourceNotFoundException{


    public UserNotFoundException(Integer id) {
        super("Unable to find user with id of " + id);
    }
}
