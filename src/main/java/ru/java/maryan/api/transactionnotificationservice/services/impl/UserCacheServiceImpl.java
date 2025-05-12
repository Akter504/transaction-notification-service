package ru.java.maryan.api.transactionnotificationservice.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.java.maryan.api.transactionnotificationservice.models.User;
import ru.java.maryan.api.transactionnotificationservice.services.UserCacheService;

import java.time.Duration;

@Service
@Slf4j
public class UserCacheServiceImpl implements UserCacheService {
    private final RedisTemplate<String, User> redisTemplate;
    private static final String PREFIX = "user::";
    private static final String PREFIX_EMAIL = PREFIX + "email::";
    private static final String PREFIX_PHONE_NUMBER = PREFIX + "phone::";

    @Value("${data.user.redis.ttl-for-id}")
    private Duration ttlForId;

    @Value("${data.user.redis.ttl-for-other}")
    private Duration ttlForAdditional;

    public UserCacheServiceImpl(RedisTemplate<String, User> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public User getFromCacheById(Long id) {
        return redisTemplate.opsForValue().get(PREFIX + id);
    }

    @Override
    public User getFromCacheByEmail(String email) {
        return redisTemplate.opsForValue().get(PREFIX_EMAIL + email);
    }

    @Override
    public User getFromCacheByPhoneNumber(String phoneNumber) {
        return redisTemplate.opsForValue().get(PREFIX_PHONE_NUMBER + phoneNumber);
    }

    @Override
    public void saveToCache(User user) {
        redisTemplate.opsForValue().set(PREFIX + user.getId(), user, ttlForId);
        redisTemplate.opsForValue().set(PREFIX_EMAIL + user.getEmail(), user, ttlForAdditional);
        redisTemplate.opsForValue().set(PREFIX_PHONE_NUMBER + user.getPhoneNumber(), user, ttlForAdditional);
    }

    @Override
    public void deleteFromCache(User user) {
        redisTemplate.delete(PREFIX + user.getId());
        redisTemplate.delete(PREFIX_EMAIL + user.getEmail());
        redisTemplate.delete(PREFIX_PHONE_NUMBER + user.getPhoneNumber());
    }
}
