package com.revinder.playgroundblog.repository;

import com.revinder.playgroundblog.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByLogin(String name);
}
