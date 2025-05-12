package ru.java.maryan.api.transactionnotificationservice.repositories.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.java.maryan.api.transactionnotificationservice.models.User;
import ru.java.maryan.api.transactionnotificationservice.services.UserCacheService;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;
    private final UserCacheService userCacheService;
    private final RowMapper<User> userRowMapper;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate, UserCacheService userCacheService) {
        this.jdbcTemplate = jdbcTemplate;
        this.userCacheService = userCacheService;
        this.userRowMapper = (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setNameUser(rs.getString("name_user"));
            user.setSurnameUser(rs.getString("surname_user"));
            user.setEmail(rs.getString("email"));
            user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            user.setPhoneNumber(rs.getString("phone_number"));
            user.setPasswordHash(rs.getString("password_hash"));
            return user;
        };
    }

    public Optional<User> getUserByEmail(String email) {
        User cachedUser = userCacheService.getFromCacheByEmail(email);
        if (cachedUser != null) {
            return Optional.of(cachedUser);
        }

        String sql = "SELECT * FROM users WHERE email = ?";
        try {
            List<User> users = jdbcTemplate.query(sql, userRowMapper, email);
            if (!users.isEmpty()) {
                User user = users.get(0);
                userCacheService.saveToCache(user);
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<User> getUserByPhoneNumber(String phoneNumber) {
        User cachedUser = userCacheService.getFromCacheByPhoneNumber(phoneNumber);
        if (cachedUser != null) {
            return Optional.of(cachedUser);
        }

        String sql = "SELECT * FROM users WHERE phone_number = ?";
        try {
            List<User> users = jdbcTemplate.query(sql, userRowMapper, phoneNumber);
            if (!users.isEmpty()) {
                User user = users.get(0);
                userCacheService.saveToCache(user);
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public boolean isUserExists(Long id) {
        User cachedUser = userCacheService.getFromCacheById(id);
        if (cachedUser != null) {
            return true;
        }

        String sql = "SELECT * FROM users WHERE id = ?";
        return !jdbcTemplate.query(sql, userRowMapper, id).isEmpty();
    }

    public User save(User user) {
        String sql;
        if (user.getId() == null) {
            sql =
            """
            INSERT INTO Users(email, phone_number, name_user, surname_user, password_hash)
            VALUES (?, ?, ?, ?, ?)
            RETURNING id
            """;
            Long id = jdbcTemplate.queryForObject(sql,
                    Long.class,
                    user.getEmail(), user.getPhoneNumber(), user.getNameUser(),
                    user.getSurnameUser(), user.getPasswordHash());
            user.setId(id);
        } else {
            sql =
            """
            UPDATE Users SET email = ?, phone_number = ?, name_user = ?, surname_user = ?,password_hash = ?
            WHERE id = ?
            """;
            jdbcTemplate.update(sql,
                    user.getEmail(), user.getPhoneNumber(), user.getNameUser(),
                    user.getSurnameUser(), user.getPasswordHash(), user.getId());
        }
        userCacheService.saveToCache(user);
        return user;
    }

    public Optional<User> getUserById(Long userId) {
        User cachedUser = userCacheService.getFromCacheById(userId);
        if (cachedUser != null) {
            return Optional.of(cachedUser);
        }

        String sql = "SELECT * FROM users WHERE id = ?";
        try {
            List<User> users = jdbcTemplate.query(sql, userRowMapper, userId);
            if (!users.isEmpty()) {
                User user = users.get(0);
                userCacheService.saveToCache(user);
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void deleteUser(Long userId) {
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, userId);
        User cachedUser = userCacheService.getFromCacheById(userId);
        if (cachedUser != null) {
            userCacheService.deleteFromCache(cachedUser);
        }
    }
}
