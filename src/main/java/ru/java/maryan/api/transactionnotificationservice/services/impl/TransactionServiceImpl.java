package ru.java.maryan.api.transactionnotificationservice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.java.maryan.api.transactionnotificationservice.dto.request.TransactionRequest;
import ru.java.maryan.api.transactionnotificationservice.models.Account;
import ru.java.maryan.api.transactionnotificationservice.models.Enums.TransactionStatus;
import ru.java.maryan.api.transactionnotificationservice.models.Transaction;
import ru.java.maryan.api.transactionnotificationservice.models.User;
import ru.java.maryan.api.transactionnotificationservice.repositories.impl.TransactionRepository;
import ru.java.maryan.api.transactionnotificationservice.services.AccountService;
import ru.java.maryan.api.transactionnotificationservice.services.TransactionService;

import java.time.LocalDateTime;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }

    @Override
    public Transaction createTransaction(TransactionRequest transactionRequest) {
        Account toAccount = accountService.findAccountByUserPhoneNumber(transactionRequest);
        accountService.debit(transactionRequest.getFromAccountId(), transactionRequest.getAmount(),
                transactionRequest.getCurrencyType(), toAccount);
        accountService.updateAccount(toAccount);

        Transaction transaction = new Transaction();
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setId(transactionRequest.getTransactionId());
        transaction.setComment(transactionRequest.getComment());
        transaction.setFromAccountId(transactionRequest.getFromAccountId());
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setToAccountId(toAccount.getId());

        return transactionRepository.save(transaction);
    }
}
