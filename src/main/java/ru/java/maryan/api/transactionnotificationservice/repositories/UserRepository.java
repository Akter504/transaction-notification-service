package ru.java.maryan.api.transactionnotificationservice.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.java.maryan.api.transactionnotificationservice.models.User;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<User> userRowMapper;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
        String sql = "SELECT * FROM users WHERE email = ?";
        try {
            List<User> users = jdbcTemplate.query(sql, userRowMapper, email);
            return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<User> getUserByPhoneNumber(String phoneNumber) {
        String sql = "SELECT * FROM users WHERE phone_number = ?";
        try {
            List<User> users = jdbcTemplate.query(sql, userRowMapper, phoneNumber);
            return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public boolean isUserExists(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        return !jdbcTemplate.query(sql, userRowMapper, id).isEmpty();
    }

    public User save(User user) {
        String sql;
        if (user.getId() == null) {
//            sql = "INSERT INTO Users(email, phone_number, name_user, surname_user, password_hash) " +
//                    "VALUES (?, ?, ?, ?, ?) RETURNING ID";
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
//            sql = "UPDATE Users SET email=?, phone_number=?, name_user=?, surname_user=?,password_hash=?" +
//                    "WHERE id=?";
            sql =
            """
            UPDATE Users SET email = ?, phone_number = ?, name_user = ?, surname_user = ?,password_hash = ?
            WHERE id = ?
            """;
            jdbcTemplate.update(sql,
                    user.getEmail(), user.getPhoneNumber(), user.getNameUser(),
                    user.getSurnameUser(), user.getPasswordHash(), user.getId());
        }
        return user;
    }
}
