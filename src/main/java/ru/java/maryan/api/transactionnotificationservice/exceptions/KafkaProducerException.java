package ru.java.maryan.api.transactionnotificationservice.exceptions;

import lombok.Getter;

@Getter
public class KafkaProducerException extends RuntimeException {
    private final String topic;
    private final Object messageKey;
    private final Object messageValue;

    public KafkaProducerException(String topic, Object messageKey, Object messageValue,
                                  String message, Throwable cause) {
        super(message, cause);
        this.topic = topic;
        this.messageKey = messageKey;
        this.messageValue = messageValue;
    }
}
