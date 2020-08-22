package com.revinder.playgroundblog.util.exceptions;


public class UserMismatchException extends RuntimeException {

    public UserMismatchException() {
        super("ID of Author does not match provided user ID.");
    }


    public UserMismatchException(String msg)
    {
        super("User mismatch: " + msg);
    }
}
