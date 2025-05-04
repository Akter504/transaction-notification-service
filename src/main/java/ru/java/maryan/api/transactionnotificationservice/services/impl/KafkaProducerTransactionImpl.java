package ru.java.maryan.api.transactionnotificationservice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.java.maryan.api.transactionnotificationservice.dto.request.TransactionRequest;
import ru.java.maryan.api.transactionnotificationservice.exceptions.KafkaProducerException;
import ru.java.maryan.api.transactionnotificationservice.models.Transaction;
import ru.java.maryan.api.transactionnotificationservice.services.KafkaProducerTransaction;

@Service
public class KafkaProducerTransactionImpl implements KafkaProducerTransaction {
    private final KafkaTemplate<String, TransactionRequest> kafkaTemplate;
    @Value("${spring.kafka.transaction-topic}")
    private String topicIn;

    @Autowired
    public KafkaProducerTransactionImpl(KafkaTemplate<String, TransactionRequest> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(TransactionRequest request) {
        try {
            kafkaTemplate.send(
                    topicIn,
                    request.getTransactionId().toString(),
                    request
            );
        } catch (Exception e) {
            throw new KafkaProducerException(
                    topicIn,
                    request.getTransactionId(),
                    request,
                    "Thread interrupted while sending transaction to Kafka",
                    e
            );
        }
    }

    @Override
    public void send(TransactionRequest request, String topic) {
        try {
            kafkaTemplate.send(
                    topic,
                    request.getTransactionId().toString(),
                    request
            );
        } catch (Exception e) {
            throw new KafkaProducerException(
                    topic,
                    request.getTransactionId(),
                    request,
                    "Thread interrupted while sending transaction to Kafka",
                    e
            );
        }
    }
}
