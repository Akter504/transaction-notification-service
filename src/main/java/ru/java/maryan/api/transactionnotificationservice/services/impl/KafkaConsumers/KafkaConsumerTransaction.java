package ru.java.maryan.api.transactionnotificationservice.services.impl.KafkaConsumers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.java.maryan.api.transactionnotificationservice.dto.request.TransactionRequest;
import ru.java.maryan.api.transactionnotificationservice.models.Transaction;
import ru.java.maryan.api.transactionnotificationservice.services.KafkaConsumer;
import ru.java.maryan.api.transactionnotificationservice.services.KafkaProducerTransaction;
import ru.java.maryan.api.transactionnotificationservice.services.TransactionService;

@Service
public class KafkaConsumerTransaction implements KafkaConsumer {
    private final TransactionService transactionService;
    private final KafkaProducerTransaction kafkaProducerTransaction;

    @Value("${spring.kafka.transaction-mongo-topic}")
    private String mongoTopic;

    @Value("${spring.kafka.transaction-receipts-topic}")
    private String receiptsTopic;

    @Value("${spring.kafka.transaction-redis-topic}")
    private String redisTopic;

    @Autowired
    public KafkaConsumerTransaction(TransactionService transactionService, KafkaProducerTransaction kafkaProducerTransaction) {
        this.transactionService = transactionService;
        this.kafkaProducerTransaction = kafkaProducerTransaction;
    }

    @KafkaListener(topics = "${spring.kafka.transaction-topic}", groupId = "${spring.kafka.group-transaction}",
            containerFactory = "transactionRequestKafkaListenerContainerFactory")
    public void processTransaction(TransactionRequest transactionRequest) {
        Transaction transaction = transactionService.createTransaction(transactionRequest);
        kafkaProducerTransaction.send(transaction, mongoTopic);
        kafkaProducerTransaction.send(transaction, receiptsTopic);
//        kafkaProducerTransaction.send(transactionRequest, notifyTopic);
//        kafkaProducerTransaction.send(transactionRequest, redisTopic);
    }
}
