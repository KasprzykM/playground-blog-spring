package com.revinder.playgroundblog.repository;

import com.revinder.playgroundblog.model.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PostRepository extends CrudRepository<Post, Long> {

    Optional<Iterable<Post>> findAllByUser_Login(String login);
}
