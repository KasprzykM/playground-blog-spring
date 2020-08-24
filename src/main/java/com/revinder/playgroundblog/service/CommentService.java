package com.revinder.playgroundblog.service;

import com.revinder.playgroundblog.model.Comment;
import com.revinder.playgroundblog.repository.CommentRepository;
import com.revinder.playgroundblog.util.exceptions.CommentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {


    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }



    public List<Comment> findAll()
    {
        return commentRepository.findAll();
    }

    public Comment findById(Long id)
    {
        return commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException(id));
    }
}
