package com.revinder.playgroundblog.service;

import com.revinder.playgroundblog.model.Comment;
import com.revinder.playgroundblog.model.Post;
import com.revinder.playgroundblog.model.User;
import com.revinder.playgroundblog.repository.CommentRepository;
import com.revinder.playgroundblog.repository.PostRepository;
import com.revinder.playgroundblog.repository.UserRepository;
import com.revinder.playgroundblog.util.exceptions.CommentNotFoundException;
import com.revinder.playgroundblog.util.exceptions.PostNotFoundException;
import com.revinder.playgroundblog.util.exceptions.UserMismatchException;
import com.revinder.playgroundblog.util.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CommentService {


    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, UserRepository userRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }


    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    public Comment findById(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException(id));
    }


    public Comment save(Comment comment, String username, Long postId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        comment.setUser(user);
        comment.setPost(post);
        return commentRepository.save(comment);
    }

    public void deleteById(Long id) {
        Comment commentToDelete = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));

        commentRepository.delete(commentToDelete);
    }

    public List<Comment> findByUserName(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        return commentRepository.findAllByUser(user);
    }


    public List<Comment> findByPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        return commentRepository.findAllByPost(post);
    }


    public Comment update(Comment newComment, Long commentIdToUpdate, String username)
    {
        Comment oldComment = commentRepository.findById(commentIdToUpdate)
                .orElseThrow(() -> new CommentNotFoundException(commentIdToUpdate));

        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        Long actualAuthorId = oldComment.getUser().getId();
        if(!Objects.equals(actualAuthorId, currentUser.getId()))
            throw new UserMismatchException("Only author can update his own comment.");


        oldComment.updateFrom(newComment);
        return  commentRepository.save(oldComment);

    }
}
