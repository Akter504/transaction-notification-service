package ru.java.maryan.api.transactionnotificationservice.services;

import ru.java.maryan.api.transactionnotificationservice.dto.request.TransactionRequest;

public interface KafkaProducerTransaction {
    void send(TransactionRequest transactionRequest);
    void send(TransactionRequest transactionRequest, String topic);
}
