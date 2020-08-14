package com.revinder.playgroundblog.repository;

import com.revinder.playgroundblog.model.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends CrudRepository<Post, Long> {

    Optional<List<Post>> findAllByUser_Login(String login);
    List<Post> findAll();
}
