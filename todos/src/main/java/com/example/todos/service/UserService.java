package com.example.todos.service;

import com.example.todos.domain.model.User;
import com.example.todos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public Optional<User> getUserFindById(Integer userId) {
        return userRepository.findById(userId);
    }
}
