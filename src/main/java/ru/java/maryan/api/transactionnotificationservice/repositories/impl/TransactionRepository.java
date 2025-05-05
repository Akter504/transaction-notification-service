package ru.java.maryan.api.transactionnotificationservice.repositories.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.java.maryan.api.transactionnotificationservice.models.Enums.TransactionStatus;
import ru.java.maryan.api.transactionnotificationservice.models.Transaction;
import ru.java.maryan.api.transactionnotificationservice.models.User;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
@Slf4j
public class TransactionRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Transaction> transactionRowMapper;

    @Autowired
    public TransactionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.transactionRowMapper = (rs, rowNum) -> {
            Transaction transaction = new Transaction();
            transaction.setId(UUID.fromString(rs.getString("id")));
            transaction.setFromAccountId(rs.getLong("from_account_id"));
            transaction.setToAccountId(rs.getLong("to_account_id"));
            transaction.setStatus(TransactionStatus.valueOf(rs.getString("status")));
            transaction.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            transaction.setComment(rs.getString("comment"));
            transaction.setAmount(rs.getLong("amount"));
            return transaction;
        };
    }

    public boolean isTransactionExists(UUID id) {
        String sql = "SELECT * FROM Transactions WHERE id = ?";
        return jdbcTemplate.query(sql, transactionRowMapper, id).isEmpty();
    }

    public Transaction save(Transaction transaction) {
        String sql;
        if (isTransactionExists(transaction.getId())) {
            sql =
                    """
                    INSERT INTO Transactions 
                        (id, from_account_id, to_account_id, amount, status, created_at, comment)
                    VALUES 
                        (?, ?, ?, ?, ?::transaction_status, ?, ?)
                    """;
            try{
                jdbcTemplate.update(sql,
                    transaction.getId(), transaction.getFromAccountId(), transaction.getToAccountId(),
                    transaction.getAmount(), transaction.getStatus().name(), transaction.getCreatedAt(),
                    transaction.getComment());
            } catch (Exception e) {
                log.error(e.toString());
            }
        } else {
            sql =
                    """
                    UPDATE transactions SET from_account_id = ?, to_account_id = ?, amount = ?, status = ?::transaction_status, 
                         comment = ?
                    WHERE id = ?
                    """;
            try {
                jdbcTemplate.update(sql,
                        transaction.getFromAccountId(), transaction.getToAccountId(),
                        transaction.getAmount(), transaction.getStatus().name(), transaction.getComment(),
                        transaction.getId());
            } catch (Exception e) {
                log.error(e.toString());
            }
        }
        return transaction;
    }
}
