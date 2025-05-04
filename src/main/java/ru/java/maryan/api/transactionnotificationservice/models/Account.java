package ru.java.maryan.api.transactionnotificationservice.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Account {
    public enum CurrencyType {
        USD,
        EUR,
        RUB
    }

    public enum AccountType {
        DEBIT,
        SAVINGS
    }

    private Long id;
    private Long balance;
    private CurrencyType currency;
    private AccountType type;
    private LocalDateTime createdAt;
    private Long userId;
}
