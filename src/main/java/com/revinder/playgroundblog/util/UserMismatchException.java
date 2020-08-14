package com.revinder.playgroundblog.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "That user had not written provided post.")
public class UserMismatchException extends RuntimeException {

    public UserMismatchException() {
        super("ID of Author does not match provided user ID.");
    }
}
