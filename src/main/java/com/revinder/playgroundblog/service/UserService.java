package com.revinder.playgroundblog.service;

import com.revinder.playgroundblog.model.User;
import com.revinder.playgroundblog.repository.UserRepository;
import com.revinder.playgroundblog.util.IncorrectBodyException;
import com.revinder.playgroundblog.util.UserDuplicateEntryException;
import com.revinder.playgroundblog.util.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {


    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> findAll() {
        return userRepository.findAll();
    }


    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public User findByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException(login));
    }

    public User save(User user) {
        User savedUser;
        if(user.getEmail() == null || user.getLogin() == null || user.getPassword() == null)
            throw new IncorrectBodyException(user.toString());
        try {
            savedUser = userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new UserDuplicateEntryException(e.getLocalizedMessage());
        }
        return savedUser;
    }

    public void deleteById(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        userRepository.deleteById(id);
    }
}
