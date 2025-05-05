package ru.java.maryan.api.transactionnotificationservice.models;

import lombok.Getter;
import lombok.Setter;
import ru.java.maryan.api.transactionnotificationservice.models.Enums.CurrencyType;

import java.time.LocalDateTime;

@Getter
@Setter
public class Account {
    private Long id;
    private Long balance;
    private CurrencyType currency;
    private LocalDateTime createdAt;
    private Long userId;
}
