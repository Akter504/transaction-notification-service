package ru.java.maryan.api.transactionnotificationservice.services;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import ru.java.maryan.api.transactionnotificationservice.dto.request.AccountRequest;
import ru.java.maryan.api.transactionnotificationservice.dto.request.TransactionRequest;
import ru.java.maryan.api.transactionnotificationservice.models.Account;
import ru.java.maryan.api.transactionnotificationservice.models.Enums.CurrencyType;
import ru.java.maryan.api.transactionnotificationservice.models.User;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    Account createAccount(AccountRequest request);

    Account findAccountByUserPhoneNumber(TransactionRequest transactionRequest);
    User findUserByAccountId(Long fromAccountId);
    List<Account> findAccountsByUserId(Long id);

    void debit(Long fromAccountId,
               @NotBlank(message = "The amount cannot be empty.") @Min(value = 1, message = "The amount is too small(min 1)") Long amount,
               @NotBlank CurrencyType currencyType,
                Account toAccount);

    void updateAccount(Account toAccount);
}
