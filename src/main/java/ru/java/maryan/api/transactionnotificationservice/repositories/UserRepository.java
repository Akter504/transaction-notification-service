package ru.java.maryan.api.transactionnotificationservice.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.java.maryan.api.transactionnotificationservice.models.User;

import java.util.Optional;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<User> userRowMapper;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate, RowMapper<User> userRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRowMapper = (rs, rowNum) -> {
            User user = new User();
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
            return Optional.ofNullable(
                    jdbcTemplate.query(sql, userRowMapper, email).getFirst()
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<User> getUserByPhoneNumber(String phoneNumber) {
        String sql = "SELECT * FROM users WHERE phone_number = ?";
        try {
            return Optional.ofNullable(
                    jdbcTemplate.query(sql, userRowMapper, phoneNumber).getFirst()
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
