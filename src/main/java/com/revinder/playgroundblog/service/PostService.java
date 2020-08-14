package com.revinder.playgroundblog.service;

import com.revinder.playgroundblog.model.Post;
import com.revinder.playgroundblog.model.User;
import com.revinder.playgroundblog.repository.PostRepository;
import com.revinder.playgroundblog.repository.UserRepository;
import com.revinder.playgroundblog.util.PostNotFoundException;
import com.revinder.playgroundblog.util.UserMismatchException;
import com.revinder.playgroundblog.util.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public List<Post> findAll()
    {
        return postRepository.findAll();
    }


    public Post findById(Long id)
    {
        return postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    public List<Post> findByUserLogin(String login)
    {
        return postRepository.findAllByUser_Login(login)
                .orElseThrow(() -> new PostNotFoundException(login));
    }

    public Post save(Post post, String userLogin)
    {
        User user = userRepository.findByLogin(userLogin)
                .orElseThrow(() -> new UserNotFoundException(userLogin));
        post.setUser(user);
        return postRepository.save(post);
    }

    public Post updatePost(Post newPost, Long postIdToUpdate, String userLogin)
    {
        User user = userRepository.findByLogin(userLogin)
                .orElseThrow(() -> new UserNotFoundException(userLogin));
        Post postToUpdate = postRepository.findById(postIdToUpdate)
                .orElseThrow(() -> new PostNotFoundException(postIdToUpdate));

        Long actualAuthorId = postToUpdate.getUser().getId();
        if(!Objects.equals(actualAuthorId, user.getId()))
            throw new UserMismatchException();

        postToUpdate.updateFrom(newPost);
        return postRepository.save(postToUpdate);
    }

    public void deleteById(Long id)
    {
        Post postToDelete = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
        postRepository.delete(postToDelete);
    }

}
