package ru.java.maryan.api.transactionnotificationservice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.java.maryan.api.transactionnotificationservice.dto.request.TransactionRequest;
import ru.java.maryan.api.transactionnotificationservice.models.Transaction;
import ru.java.maryan.api.transactionnotificationservice.repositories.impl.TransactionRepository;
import ru.java.maryan.api.transactionnotificationservice.services.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction createTransaction(TransactionRequest transactionRequest) {
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setStatus(Transaction.TransactionStatus.SUCCESS);
        transaction.setId(transactionRequest.getTransactionId());
        transaction.setComment(transactionRequest.getComment());
        transaction.setFromAccountId(transactionRequest.getFromAccountId());
        transaction.setToAccountId(transactionRequest.getToAccountId());

        return transactionRepository.save(transaction);
    }
}
