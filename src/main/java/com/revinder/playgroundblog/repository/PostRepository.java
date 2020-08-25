package com.revinder.playgroundblog.repository;

import com.revinder.playgroundblog.model.Post;
import com.revinder.playgroundblog.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {

    List<Post> findAllByUser(User user);
    List<Post> findAll();
}
