package com.revinder.playgroundblog.util.exceptions.advisors;

import com.revinder.playgroundblog.util.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {


    private static final String RESOURCE_NOT_FOUND = "ResourceNotFoundError";
    private static final String DUPLICATE_ENTRY_FOUND = "DuplicateUserFoundError";
    private static final String INCORRECT_BODY = "IncorrectBodyError";
    private static final String USER_MISMATCH = "UserMismatchError";

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<?> handleResourceNotFound(ResourceNotFoundException e, HttpServletRequest request)
    {
        return responseBuilder(e, RESOURCE_NOT_FOUND, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({UserDuplicateEntryException.class})
    public ResponseEntity<?> handleDuplicateUser(UserDuplicateEntryException e, HttpServletRequest request)
    {
        return responseBuilder(e, DUPLICATE_ENTRY_FOUND, request, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({IncorrectBodyException.class})
    public ResponseEntity<?> handleDuplicateUser(IncorrectBodyException e, HttpServletRequest request)
    {
        return responseBuilder(e, INCORRECT_BODY, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UserMismatchException.class})
    public ResponseEntity<?> handleMismatchedUser(UserMismatchException e, HttpServletRequest request)
    {
        return responseBuilder(e, USER_MISMATCH, request, HttpStatus.CONFLICT);
    }


//    public ResponseEntity<?> handleBadRequest(Exception e)


    private ResponseEntity<Object> responseBuilder(Exception e,
                                            final String errorName,
                                            HttpServletRequest request,
                                            final HttpStatus status)
    {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());
        body.put("error", errorName);
        body.put("message", e.getMessage());
        body.put("path", request.getRequestURI());
        return new ResponseEntity<>(body, status);
    }

}
