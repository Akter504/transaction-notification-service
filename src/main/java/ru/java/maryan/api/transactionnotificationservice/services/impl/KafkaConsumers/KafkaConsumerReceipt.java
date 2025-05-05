package ru.java.maryan.api.transactionnotificationservice.services.impl.KafkaConsumers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.java.maryan.api.transactionnotificationservice.models.Transaction;
import ru.java.maryan.api.transactionnotificationservice.models.User;
import ru.java.maryan.api.transactionnotificationservice.services.AccountService;
import ru.java.maryan.api.transactionnotificationservice.services.impl.S3Service;
import ru.java.maryan.api.transactionnotificationservice.utils.PdfGenerator;

@Service
public class KafkaConsumerReceipt {
    private final AccountService accountService;
    private final S3Service s3Service;

    @Autowired
    public KafkaConsumerReceipt(AccountService accountService, S3Service s3Service) {
        this.accountService = accountService;
        this.s3Service = s3Service;
    }

    @KafkaListener(topics = "${spring.kafka.transaction-receipts-topic}", groupId = "${spring.kafka.group-transaction}",
            containerFactory = "transactionKafkaListenerContainerFactory")
    public void processTransaction(Transaction transaction) {
        User fromUser = accountService.findUserByAccountId(transaction.getFromAccountId());
        User toUser = accountService.findUserByAccountId(transaction.getToAccountId());
        s3Service.uploadReceipt(transaction.getId().toString(), PdfGenerator.generateReceipt(transaction, fromUser, toUser));
    }
}
