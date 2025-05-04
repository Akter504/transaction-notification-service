package ru.java.maryan.api.transactionnotificationservice.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.java.maryan.api.transactionnotificationservice.models.TransactionMongo;

import java.util.UUID;

public interface TransactionMongoRepository extends MongoRepository<TransactionMongo, UUID> {
}
