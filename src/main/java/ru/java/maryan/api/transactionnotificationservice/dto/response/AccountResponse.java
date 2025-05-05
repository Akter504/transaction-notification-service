package ru.java.maryan.api.transactionnotificationservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.java.maryan.api.transactionnotificationservice.models.Enums.CurrencyType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountResponse {
    private Long id;
    private Long balance;
    private CurrencyType currencyType;
}
