package ru.java.maryan.api.transactionnotificationservice.services;

import ru.java.maryan.api.transactionnotificationservice.dto.request.TransactionRequest;

public interface KafkaConsumer {
    void processTransaction(TransactionRequest transactionRequest);
}
