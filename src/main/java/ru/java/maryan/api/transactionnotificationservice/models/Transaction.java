package ru.java.maryan.api.transactionnotificationservice.models;

import lombok.Getter;
import lombok.Setter;
import ru.java.maryan.api.transactionnotificationservice.models.Enums.TransactionStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class Transaction {
    private UUID id;
    private Long fromAccountId;
    private Long toAccountId;
    private Long amount;
    private TransactionStatus status;
    private LocalDateTime createdAt;
    private String comment;
}
