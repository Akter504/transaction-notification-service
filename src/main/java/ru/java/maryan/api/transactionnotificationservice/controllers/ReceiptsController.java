package ru.java.maryan.api.transactionnotificationservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Tag(
        name = "Receipt Controller",
        description = "Handles downloading PDF receipts"
)
public interface ReceiptsController {
    @Operation(
            summary = "Download receipt PDF",
            description = "Downloads a PDF receipt for the given transaction ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Receipt downloaded successfully"),
                    @ApiResponse(responseCode = "404", description = "Receipt not found"),
                    @ApiResponse(responseCode = "500", description = "Internal error")
            }
    )
    @GetMapping("/{transactionId}")
    ResponseEntity<byte[]> getReceipt(
            @Parameter(description = "UUID of the transaction") @PathVariable("transactionId") UUID transactionId
    );
}
