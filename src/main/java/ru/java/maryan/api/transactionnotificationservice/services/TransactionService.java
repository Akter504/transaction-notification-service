package ru.java.maryan.api.transactionnotificationservice.services;

import ru.java.maryan.api.transactionnotificationservice.dto.request.TransactionRequest;
import ru.java.maryan.api.transactionnotificationservice.models.Transaction;

public interface TransactionService {
    Transaction createTransaction(TransactionRequest transactionRequest);
}
