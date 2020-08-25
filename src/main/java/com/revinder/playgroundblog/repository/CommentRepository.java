package com.revinder.playgroundblog.repository;

import com.revinder.playgroundblog.model.Comment;
import com.revinder.playgroundblog.model.Post;
import com.revinder.playgroundblog.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment,Long> {


    List<Comment> findAllByUser(User user);
    List<Comment> findAllByPost(Post post);
    List<Comment> findAll();
}
