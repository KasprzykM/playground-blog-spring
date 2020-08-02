package com.revinder.playgroundblog.service;

import com.revinder.playgroundblog.model.User;
import com.revinder.playgroundblog.repository.UserRepository;
import com.revinder.playgroundblog.util.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public Iterable<User> findAll()
    {
        return userRepository.findAll();
    }


    public User findById(Integer id)
    {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public User findByLogin(String login)
    {
        return userRepository.findByLogin(login);
    }

    public User save(User user)
    {
        return userRepository.save(user);
    }

    public void deleteById(Integer id)
    {
        userRepository.deleteById(id);
    }
}
