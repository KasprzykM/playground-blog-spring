package com.revinder.playgroundblog.repository;

import com.revinder.playgroundblog.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User findByLogin(String name);
}
