package ru.java.maryan.api.transactionnotificationservice.services;

import ru.java.maryan.api.transactionnotificationservice.dto.request.TransactionRequest;
import ru.java.maryan.api.transactionnotificationservice.models.Transaction;
import ru.java.maryan.api.transactionnotificationservice.models.User;

public interface TransactionService {
    Transaction createTransaction(TransactionRequest transactionRequest);
}
