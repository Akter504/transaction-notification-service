package ru.java.maryan.api.transactionnotificationservice.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.java.maryan.api.transactionnotificationservice.models.Transaction;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionRequest {
    private UUID transactionId;
    private Long fromAccountId;
    private Long toAccountId;

    @NotBlank(message = "The amount cannot be empty.")
    @Min(value = 1, message = "The amount is too small(min 1)")
    private Long amount;
    private Transaction.TransactionStatus status;
    private String comment;
}
