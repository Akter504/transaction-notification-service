package ru.java.maryan.api.transactionnotificationservice.services;

import ru.java.maryan.api.transactionnotificationservice.dto.request.TransactionRequest;

public interface KafkaProducerTransaction {
    void send(Object request);
    void send(Object request, String topic);
}
