package ru.java.maryan.api.transactionnotificationservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.java.maryan.api.transactionnotificationservice.models.Account;
import ru.java.maryan.api.transactionnotificationservice.models.Enums.CurrencyType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountRequest {
    private Long balance; // need?
    private CurrencyType currencyType;
    private Long userId;
}
