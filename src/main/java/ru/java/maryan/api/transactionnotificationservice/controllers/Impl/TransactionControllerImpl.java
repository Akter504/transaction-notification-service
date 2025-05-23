package ru.java.maryan.api.transactionnotificationservice.controllers.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.java.maryan.api.transactionnotificationservice.controllers.TransactionController;
import ru.java.maryan.api.transactionnotificationservice.dto.request.TransactionRequest;
import ru.java.maryan.api.transactionnotificationservice.dto.response.TransactionResponse;
import ru.java.maryan.api.transactionnotificationservice.models.Enums.TransactionStatus;
import ru.java.maryan.api.transactionnotificationservice.services.KafkaProducerTransaction;

import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api/transaction")
public class TransactionControllerImpl implements TransactionController {
    private final KafkaProducerTransaction kafkaProducer;

    @Autowired
    public TransactionControllerImpl(KafkaProducerTransaction kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(@Validated @RequestBody TransactionRequest request) {
        UUID transactionId = UUID.randomUUID();
        request.setTransactionId(transactionId);
        request.setStatus(TransactionStatus.PENDING);

        kafkaProducer.send(request);
        TransactionResponse transactionResponse = TransactionResponse.builder()
                .transactionId(transactionId)
                .status(TransactionStatus.PENDING)
                .build();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(transactionResponse);
    }
}
