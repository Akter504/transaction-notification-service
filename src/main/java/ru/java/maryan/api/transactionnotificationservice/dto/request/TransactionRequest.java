package ru.java.maryan.api.transactionnotificationservice.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.java.maryan.api.transactionnotificationservice.models.Enums.CurrencyType;
import ru.java.maryan.api.transactionnotificationservice.models.Enums.TransactionStatus;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionRequest {
    private UUID transactionId;
    private Long fromAccountId;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Uncorrected number.")
    private String toUserPhoneNumber;

    @Min(value = 1, message = "The amount is too small(min 1)")
    private Long amount;

    private CurrencyType currencyType;

    private TransactionStatus status;
    private String comment;
}
