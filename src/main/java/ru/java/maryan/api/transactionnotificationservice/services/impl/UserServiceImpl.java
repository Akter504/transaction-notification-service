package ru.java.maryan.api.transactionnotificationservice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.java.maryan.api.transactionnotificationservice.models.User;
import ru.java.maryan.api.transactionnotificationservice.repositories.impl.UserRepository;
import ru.java.maryan.api.transactionnotificationservice.services.UserService;

import java.util.Optional;
import java.util.OptionalInt;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Override
    public Optional<User> findUserByPhoneNumber(String phoneNumber) {
        return userRepository.getUserByPhoneNumber(phoneNumber);
    }

    @Override
    public Optional<User> findUserById(Long userId) {
        return userRepository.getUserById(userId);
    }

    @Override
    public boolean isUserExist(Long id) {
        return userRepository.isUserExists(id);
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteUser(userId);
    }
}

