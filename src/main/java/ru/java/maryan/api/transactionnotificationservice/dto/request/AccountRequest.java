package ru.java.maryan.api.transactionnotificationservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.java.maryan.api.transactionnotificationservice.models.Account;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountRequest {
    private Long balance;
    private Account.AccountType accountType;
    private Account.CurrencyType currencyType;
    private Long userId;
}
