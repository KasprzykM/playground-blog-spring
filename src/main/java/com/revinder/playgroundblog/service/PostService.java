package com.revinder.playgroundblog.service;

import com.revinder.playgroundblog.model.Post;
import com.revinder.playgroundblog.model.User;
import com.revinder.playgroundblog.repository.PostRepository;
import com.revinder.playgroundblog.repository.UserRepository;
import com.revinder.playgroundblog.util.PostNotFoundException;
import com.revinder.playgroundblog.util.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Iterable<Post> findAll()
    {
        return postRepository.findAll();
    }


    public Post findById(Long id)
    {
        return postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    public Iterable<Post> findByUserLogin(String login)
    {
        return postRepository.findAllByUser_Login(login)
                .orElseThrow(() -> new PostNotFoundException(login));
    }

    public Post save(Post post, String userLogin)
    {
        Optional<User> user = userRepository.findByLogin(userLogin);
        post.setUser(user.
                orElseThrow(() -> new UserNotFoundException(userLogin)));
        return postRepository.save(post);
    }

    public void deleteById(Long id)
    {
        postRepository.deleteById(id);
    }

}
