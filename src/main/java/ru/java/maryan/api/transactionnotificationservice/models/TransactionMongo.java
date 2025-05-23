package ru.java.maryan.api.transactionnotificationservice.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.java.maryan.api.transactionnotificationservice.models.Enums.TransactionStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Document
public class TransactionMongo {
    @Id
    private UUID id;
    private Long fromAccountId;
    private Long toAccountId;
    private Long amount;
    private TransactionStatus status;
    private LocalDateTime createdAt;
    private String comment;
}
