package ru.java.maryan.api.transactionnotificationservice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.java.maryan.api.transactionnotificationservice.dto.request.AccountRequest;
import ru.java.maryan.api.transactionnotificationservice.models.Account;
import ru.java.maryan.api.transactionnotificationservice.repositories.impl.AccountRepository;
import ru.java.maryan.api.transactionnotificationservice.services.AccountService;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account createAccount(AccountRequest request) {
        Account account = new Account();
        account.setBalance(request.getBalance());
        account.setUserId(request.getUserId());
        account.setCurrency(request.getCurrencyType());
        account.setType(request.getAccountType());

        return accountRepository.save(account);
    }
}
