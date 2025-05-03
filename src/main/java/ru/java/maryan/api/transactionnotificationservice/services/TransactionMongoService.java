package ru.java.maryan.api.transactionnotificationservice.services;

import ru.java.maryan.api.transactionnotificationservice.dto.request.TransactionRequest;
import ru.java.maryan.api.transactionnotificationservice.models.TransactionMongo;

public interface TransactionMongoService {
    TransactionMongo createTransaction(TransactionRequest transactionRequest);
}
