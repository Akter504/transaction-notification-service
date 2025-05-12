package ru.java.maryan.api.transactionnotificationservice.services;

import ru.java.maryan.api.transactionnotificationservice.models.User;

public interface UserCacheService {
    User getFromCacheById(Long id);

    User getFromCacheByEmail(String email);

    User getFromCacheByPhoneNumber(String phoneNumber);

    void saveToCache(User user);

    void deleteFromCache(User user);
}
