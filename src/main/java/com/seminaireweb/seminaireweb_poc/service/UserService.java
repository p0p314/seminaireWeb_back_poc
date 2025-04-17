package com.seminaireweb.seminaireweb_poc.service;

import com.seminaireweb.seminaireweb_poc.entity.User;
import com.seminaireweb.seminaireweb_poc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
