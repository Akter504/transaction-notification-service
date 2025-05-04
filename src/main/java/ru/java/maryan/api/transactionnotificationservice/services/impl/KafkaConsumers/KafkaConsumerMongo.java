package ru.java.maryan.api.transactionnotificationservice.services.impl.KafkaConsumers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.java.maryan.api.transactionnotificationservice.dto.request.TransactionRequest;
import ru.java.maryan.api.transactionnotificationservice.services.KafkaConsumer;
import ru.java.maryan.api.transactionnotificationservice.services.KafkaProducerTransaction;
import ru.java.maryan.api.transactionnotificationservice.services.TransactionMongoService;

@Service
public class KafkaConsumerMongo implements KafkaConsumer {
    private final KafkaProducerTransaction kafkaProducerTransaction;
    private final TransactionMongoService transactionMongoService;

    @Autowired
    public KafkaConsumerMongo(KafkaProducerTransaction kafkaProducerTransaction, TransactionMongoService transactionMongoService) {
        this.kafkaProducerTransaction = kafkaProducerTransaction;
        this.transactionMongoService = transactionMongoService;
    }

    @KafkaListener(topics = "${spring.kafka.transaction-mongo-topic}", groupId = "${spring.kafka.group-transaction}",
            containerFactory = "transactionKafkaListenerContainerFactory")
    @Override
    public void processTransaction(TransactionRequest transactionRequest) {
        transactionMongoService.createTransaction(transactionRequest);
    }
}
