package ru.java.maryan.api.transactionnotificationservice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.java.maryan.api.transactionnotificationservice.dto.request.AccountRequest;
import ru.java.maryan.api.transactionnotificationservice.dto.request.TransactionRequest;
import ru.java.maryan.api.transactionnotificationservice.models.Account;
import ru.java.maryan.api.transactionnotificationservice.models.Enums.CurrencyType;
import ru.java.maryan.api.transactionnotificationservice.models.User;
import ru.java.maryan.api.transactionnotificationservice.repositories.impl.AccountRepository;
import ru.java.maryan.api.transactionnotificationservice.repositories.impl.UserRepository;
import ru.java.maryan.api.transactionnotificationservice.services.AccountService;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Account createAccount(AccountRequest request) {
        Account account = new Account();
        account.setBalance(request.getBalance());
        account.setUserId(request.getUserId());
        account.setCurrency(request.getCurrencyType());

        return accountRepository.save(account);
    }

    @Override
    public Account findAccountByUserPhoneNumber(TransactionRequest transactionRequest) {
        User user = userRepository.getUserByPhoneNumber(transactionRequest.getToUserPhoneNumber())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found with phone number: " + transactionRequest.getToUserPhoneNumber()
                ));
        return accountRepository.getAccountByUserId(user.getId(), transactionRequest.getCurrencyType())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Account not found with phone number: " + transactionRequest.getToUserPhoneNumber()
                ));
    }

    @Override
    public void debit(Long fromAccountId, Long amount, CurrencyType currencyType, Account toAccount) {
        validateSufficientFunds(fromAccountId, amount, currencyType);
        toAccount.setBalance(toAccount.getBalance() + amount);
    }

    @Override
    public void updateAccount(Account toAccount) {
        accountRepository.save(toAccount);
    }

    @Override
    public User findUserByAccountId(Long fromAccountId) {
        Account account = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "From account is wrong\n"
        ));
        return userRepository.getUserById(account.getUserId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found: user_id = " + account.getUserId().toString()
                ));
    }


    private void validateSufficientFunds(Long fromAccountId, Long amount, CurrencyType currencyType) {
        Account account = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "From account is wrong\n"
                ));
        if (account.getBalance().compareTo(amount) < 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Insufficient funds. Available: " + account.getBalance()
            );
        }
        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);
    }
}
