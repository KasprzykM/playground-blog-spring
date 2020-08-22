package com.revinder.playgroundblog.util.exceptions;


public class UserDuplicateEntryException extends RuntimeException{

    public UserDuplicateEntryException(String msg)
    {
        super("There is already user registered with: " + msg);
    }
}
