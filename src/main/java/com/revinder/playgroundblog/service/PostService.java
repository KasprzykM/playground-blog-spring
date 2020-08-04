package com.revinder.playgroundblog.service;

import com.revinder.playgroundblog.model.Post;
import com.revinder.playgroundblog.repository.PostRepository;
import com.revinder.playgroundblog.util.PostNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Iterable<Post> findAll()
    {
        return postRepository.findAll();
    }


    public Post findById(Long id)
    {
        return postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));
    }

    public Iterable<Post> findByUserLogin(String login)
    {
        return postRepository.findAllByUser_Login(login);
    }

    public Post save(Post post)
    {
        return postRepository.save(post);
    }

    public void deleteById(Long id)
    {
        postRepository.deleteById(id);
    }

}
