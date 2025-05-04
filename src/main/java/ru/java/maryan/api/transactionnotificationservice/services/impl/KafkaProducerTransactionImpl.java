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
    private final KafkaTemplate<String, Object> kafkaTemplate;
    @Value("${spring.kafka.transaction-topic}")
    private String topicIn;

    @Autowired
    public KafkaProducerTransactionImpl(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(Object request) {
        try {
            switch (request) {
                case Transaction transaction ->
                    kafkaTemplate.send(topicIn, transaction.getId().toString(), transaction);
                case TransactionRequest txRequest ->
                    kafkaTemplate.send(topicIn, txRequest.getTransactionId().toString(), txRequest);
                default ->
                    throw new IllegalArgumentException("Unsupported message type: " + request.getClass());
            }
        } catch (Exception e) {
            throw new KafkaProducerException(
                    topicIn,
                    request.getClass(),
                    request,
                    "Thread interrupted while sending transaction to Kafka",
                    e
            );
        }
    }

    @Override
    public void send(Object request, String topic) {
        try {
            switch (request) {
                case Transaction transaction ->
                        kafkaTemplate.send(topic, transaction.getId().toString(), transaction);
                case TransactionRequest txRequest ->
                        kafkaTemplate.send(topic, txRequest.getTransactionId().toString(), txRequest);
                default ->
                        throw new IllegalArgumentException("Unsupported message type: " + request.getClass());
            }
        } catch (Exception e) {
            throw new KafkaProducerException(
                    topic,
                    request.getClass(),
                    request,
                    "Thread interrupted while sending transaction to Kafka",
                    e
            );
        }
    }
}
