package ru.java.maryan.api.transactionnotificationservice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.java.maryan.api.transactionnotificationservice.dto.request.TransactionRequest;
import ru.java.maryan.api.transactionnotificationservice.models.TransactionMongo;
import ru.java.maryan.api.transactionnotificationservice.repositories.TransactionMongoRepository;
import ru.java.maryan.api.transactionnotificationservice.services.TransactionMongoService;

@Service
public class TransactionMongoServiceImpl implements TransactionMongoService {
    private final TransactionMongoRepository transactionMR;

    @Autowired
    public TransactionMongoServiceImpl(TransactionMongoRepository transactionMR) {
        this.transactionMR = transactionMR;
    }

    @Override
    public TransactionMongo createTransaction(TransactionRequest transactionRequest) {
        TransactionMongo transaction = new TransactionMongo();
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setStatus(TransactionMongo.TransactionStatus.SUCCESS);
        transaction.setId(transactionRequest.getTransactionId());
        transaction.setComment(transactionRequest.getComment());
        transaction.setFromAccountId(transactionRequest.getFromAccountId());
        transaction.setToAccountId(transactionRequest.getToAccountId());

        return transactionMR.save(transaction);
    }
}
