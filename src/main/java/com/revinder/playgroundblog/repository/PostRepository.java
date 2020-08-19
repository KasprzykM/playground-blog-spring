package com.revinder.playgroundblog.repository;

import com.revinder.playgroundblog.model.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {

    Optional<List<Post>> findAllByUser_Username(String username);
    List<Post> findAll();
}
