package ru.java.maryan.api.transactionnotificationservice.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.java.maryan.api.transactionnotificationservice.models.Account;
import ru.java.maryan.api.transactionnotificationservice.models.Transaction;

import java.util.UUID;

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
            account.setType(Account.AccountType.valueOf(rs.getString("type")));
            account.setCurrency(Account.CurrencyType.valueOf(rs.getString("currency")));
            account.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            account.setBalance(rs.getLong("balance"));
            return account;
        };
    }

    public Account save(Account account) {
        String sql;
        if (account.getId() == null) {
            sql =
                    """
                    INSERT INTO Accounts(balance, currency, type, user_id)
                    VALUES (?, ?::currency_type, ?::account_type, ?)
                    RETURNING id
                    """;
            Long id = jdbcTemplate.queryForObject(sql,
                    Long.class,
                    account.getBalance(), account.getCurrency().name(),
                    account.getType().name(), account.getUserId());
            account.setId(id);
        } else {
            sql =
                    """
                    UPDATE Accounts SET balance = ?, currency = ?::currency_type, type = ?::account_type, user_id = ?
                    WHERE id = ?
                    """;
            jdbcTemplate.update(sql,
                    account.getBalance(), account.getCurrency().name(),
                    account.getType().name(), account.getUserId(), account.getId());
        }
        return account;
    }
}
