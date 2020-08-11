package com.revinder.playgroundblog.repository;

import com.revinder.playgroundblog.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByLogin(String name);
    List<User> findAll();
}
