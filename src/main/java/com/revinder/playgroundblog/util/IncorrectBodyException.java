package com.revinder.playgroundblog.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Incorrect or missing body.")
public class IncorrectBodyException extends RuntimeException{

    public IncorrectBodyException(String msg)
    {
        super("Incorrect or missing body:" + msg);
    }

    public IncorrectBodyException(String msg, Throwable ex)
    {
        super("Incorrect or missing body:" + msg, ex);
    }
}
