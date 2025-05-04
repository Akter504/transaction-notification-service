package ru.java.maryan.api.transactionnotificationservice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.java.maryan.api.transactionnotificationservice.dto.request.TransactionRequest;
import ru.java.maryan.api.transactionnotificationservice.models.Account;
import ru.java.maryan.api.transactionnotificationservice.models.Enums.TransactionStatus;
import ru.java.maryan.api.transactionnotificationservice.models.Transaction;
import ru.java.maryan.api.transactionnotificationservice.models.TransactionMongo;
import ru.java.maryan.api.transactionnotificationservice.repositories.TransactionMongoRepository;
import ru.java.maryan.api.transactionnotificationservice.services.AccountService;
import ru.java.maryan.api.transactionnotificationservice.services.TransactionMongoService;

@Service
public class TransactionMongoServiceImpl implements TransactionMongoService {
    private final TransactionMongoRepository transactionMR;
    private final AccountService accountService;

    @Autowired
    public TransactionMongoServiceImpl(TransactionMongoRepository transactionMR, AccountService accountService) {
        this.transactionMR = transactionMR;
        this.accountService = accountService;
    }

    @Override
    public TransactionMongo createTransaction(Transaction transaction) {
        TransactionMongo transactionMongo = new TransactionMongo();
        transactionMongo.setAmount(transaction.getAmount());
        transactionMongo.setStatus(TransactionStatus.SUCCESS);
        transactionMongo.setId(transaction.getId());
        transactionMongo.setComment(transaction.getComment());
        transactionMongo.setFromAccountId(transaction.getFromAccountId());
        transactionMongo.setToAccountId(transaction.getToAccountId());
        transactionMongo.setCreatedAt(java.time.LocalDateTime.now());

        return transactionMR.save(transactionMongo);
    }
}
