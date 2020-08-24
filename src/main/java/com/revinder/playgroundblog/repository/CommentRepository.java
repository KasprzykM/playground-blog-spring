package com.revinder.playgroundblog.repository;

import com.revinder.playgroundblog.model.Comment;
import com.revinder.playgroundblog.model.Post;
import com.revinder.playgroundblog.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends CrudRepository<Comment,Long> {


    Optional<List<Comment>> findAllByUser(User user);
    Optional<List<Comment>> findAllByPost(Post post);
    List<Comment> findAll();
}
