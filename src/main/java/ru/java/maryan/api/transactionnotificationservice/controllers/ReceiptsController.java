package ru.java.maryan.api.transactionnotificationservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.java.maryan.api.transactionnotificationservice.services.impl.S3Service;

import java.util.UUID;

@RestController
@RequestMapping("/api/receipt")
public class ReceiptsController {
    private final S3Service s3Service;

    @Autowired
    public ReceiptsController(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<byte[]> getReceipt(@PathVariable("transactionId") UUID transactionId) {
        byte[] pdfBytes = s3Service.downloadReceipt(transactionId.toString());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition
                .attachment()
                .filename("receipt_" + transactionId + ".pdf")
                .build());

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}
