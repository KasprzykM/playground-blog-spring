package com.revinder.playgroundblog.util.exceptions;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String msg)
    {
        super(msg);
    }

    public ResourceNotFoundException(String msg, Throwable ex)
    {
        super(msg, ex);
    }
}
