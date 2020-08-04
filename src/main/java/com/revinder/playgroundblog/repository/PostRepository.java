package com.revinder.playgroundblog.repository;

import com.revinder.playgroundblog.model.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {

    Iterable<Post> findAllByUser_Login(String login);
}
