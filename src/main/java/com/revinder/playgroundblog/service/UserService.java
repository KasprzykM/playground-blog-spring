package com.revinder.playgroundblog.service;

import com.revinder.playgroundblog.model.User;
import com.revinder.playgroundblog.repository.UserRepository;
import com.revinder.playgroundblog.util.exceptions.IncorrectBodyException;
import com.revinder.playgroundblog.util.exceptions.UserDuplicateEntryException;
import com.revinder.playgroundblog.util.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {


    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bcryptEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bcryptEncoder) {
        this.userRepository = userRepository;
        this.bcryptEncoder = bcryptEncoder;
    }


    public List<User> findAll() {
        return userRepository.findAll();
    }


    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    public User save(User user) {
        user.setRoles(Set.of(User.Role.USER));
        User savedUser;
        if(user.getEmail() == null || user.getUsername() == null || user.getPassword() == null || !user.isEmailValid())
            throw new IncorrectBodyException("insufficient user definition.");
        try {
            user.setPassword(bcryptEncoder.encode(user.getPassword()));
            savedUser = userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new UserDuplicateEntryException("email: " + user.getEmail() + " or login: " + user.getUsername());
        }
        return savedUser;
    }

    public void deleteById(Long id) {
        User userToDelete = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        userRepository.delete(userToDelete);
    }


    public User update(User user, Long userIdToUpdate)
    {
        User userToUpdate = userRepository.findById(userIdToUpdate)
                .orElseThrow(() -> new UserNotFoundException(userIdToUpdate));
        userToUpdate.updateFrom(user);
        userToUpdate.setPassword(bcryptEncoder.encode(userToUpdate.getPassword()));
        return userRepository.save(userToUpdate);
    }

    private Set<? extends GrantedAuthority> getAuthority(User user)
    {
        HashSet<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        });
        return authorities;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
    }
}
