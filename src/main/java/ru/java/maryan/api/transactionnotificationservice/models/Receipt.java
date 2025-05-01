package ru.java.maryan.api.transactionnotificationservice.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class Receipt {
    private UUID id;
    private UUID transactionId;
    private String fileName;
    private String fileExtension;
    private Long fileSize;
    private String mimeType;
    private String s3Bucket;
    private String s3Key;
    private LocalDateTime uploadedAt;
}
