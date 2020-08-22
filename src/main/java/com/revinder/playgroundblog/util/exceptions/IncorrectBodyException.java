package com.revinder.playgroundblog.util.exceptions;

public class IncorrectBodyException extends RuntimeException{

    public IncorrectBodyException(String msg)
    {
        super("Incorrect or missing body: " + msg);
    }

    public IncorrectBodyException(String msg, Throwable ex)
    {
        super("Incorrect or missing body:" + msg, ex);
    }
}
