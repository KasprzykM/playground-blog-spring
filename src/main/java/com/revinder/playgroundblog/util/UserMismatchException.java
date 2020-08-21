package com.revinder.playgroundblog.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Usernames don't match.")
public class UserMismatchException extends RuntimeException {

    public UserMismatchException() {
        super("ID of Author does not match provided user ID.");
    }


    public UserMismatchException(String msg)
    {
        super("User mismatch: " + msg);
    }
}
