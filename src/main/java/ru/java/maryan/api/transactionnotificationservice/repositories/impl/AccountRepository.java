package ru.java.maryan.api.transactionnotificationservice.repositories.impl;

import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.java.maryan.api.transactionnotificationservice.models.Account;
import ru.java.maryan.api.transactionnotificationservice.models.Enums.CurrencyType;
import ru.java.maryan.api.transactionnotificationservice.models.User;

import java.util.List;
import java.util.Optional;

@Repository
public class AccountRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Account> accountRowMapper;

    @Autowired
    public AccountRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.accountRowMapper = (rs, rowNum) -> {
            Account account = new Account();
            account.setId(rs.getLong("id"));
            account.setCurrency(CurrencyType.valueOf(rs.getString("currency")));
            account.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            account.setBalance(rs.getLong("balance"));
            account.setUserId(rs.getLong("user_id"));
            return account;
        };
    }

    public Account save(Account account) {
        String sql;
        if (account.getId() == null) {
            sql =
                    """
                    INSERT INTO Accounts(balance, currency, user_id)
                    VALUES (?, ?::currency_type, ?)
                    RETURNING id
                    """;
            Long id = jdbcTemplate.queryForObject(sql,
                    Long.class,
                    account.getBalance(), account.getCurrency().name(),
                    account.getUserId());
            account.setId(id);
        } else {
            sql =
                    """
                    UPDATE Accounts SET balance = ?, currency = ?::currency_type, user_id = ?
                    WHERE id = ?
                    """;
            jdbcTemplate.update(sql,
                    account.getBalance(), account.getCurrency().name(),
                    account.getUserId(), account.getId());
        }
        return account;
    }

    public Optional<Account> getAccountByUserId(Long userId, @NotBlank CurrencyType currencyType) {
        String sql =
                """
                SELECT * FROM Accounts WHERE user_id = ? and currency = ?::currency_type     
                """;
        try {
            List<Account> accounts = jdbcTemplate.query(sql, accountRowMapper, userId, currencyType.name());
            return accounts.isEmpty() ? Optional.empty() : Optional.of(accounts.get(0));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Account> findById(Long fromAccountId) {
        String sql =
                """
                SELECT * FROM Accounts WHERE id = ?     
                """;
        try {
            List<Account> accounts = jdbcTemplate.query(sql, accountRowMapper, fromAccountId);
            return accounts.isEmpty() ? Optional.empty() : Optional.of(accounts.get(0));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
