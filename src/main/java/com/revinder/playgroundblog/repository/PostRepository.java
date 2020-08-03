package com.revinder.playgroundblog.repository;

import com.revinder.playgroundblog.model.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {

    Post findByUser_Login(String login);
}
