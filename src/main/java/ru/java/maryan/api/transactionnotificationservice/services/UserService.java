package ru.java.maryan.api.transactionnotificationservice.services;

import ru.java.maryan.api.transactionnotificationservice.models.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByPhoneNumber(String phoneNumber);
    Optional<User> findUserById(Long userId);

    boolean isUserExist(Long id);

    User createUser(User user);
    void deleteUser(Long userId);
}
