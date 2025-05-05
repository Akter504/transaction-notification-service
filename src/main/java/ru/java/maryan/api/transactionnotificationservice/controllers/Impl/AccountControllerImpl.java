package ru.java.maryan.api.transactionnotificationservice.controllers.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.java.maryan.api.transactionnotificationservice.controllers.AccountController;
import ru.java.maryan.api.transactionnotificationservice.dto.request.AccountRequest;
import ru.java.maryan.api.transactionnotificationservice.dto.response.AccountResponse;
import ru.java.maryan.api.transactionnotificationservice.models.Account;
import ru.java.maryan.api.transactionnotificationservice.services.AccountService;

@Validated
@RestController
@RequestMapping("/api/account")
@Slf4j
public class AccountControllerImpl implements AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountControllerImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Validated @RequestBody AccountRequest request) {
        log.info("ACCOUNT ID: " + request.getUserId());
        log.info("CURRENCY ACCOUNT: " + request.getCurrencyType());
        Account account = accountService.createAccount(request);
        AccountResponse response = AccountResponse.builder()
                .id(account.getId())
                .balance(account.getBalance())
                .currencyType(account.getCurrency())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
