package ru.java.maryan.api.transactionnotificationservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import ru.java.maryan.api.transactionnotificationservice.dto.request.TransactionRequest;
import ru.java.maryan.api.transactionnotificationservice.dto.response.TransactionResponse;

@Tag(
        name = "Transaction Controller",
        description = "Manages transaction creation and Kafka publishing"
)
public interface TransactionController {
    @Operation(
            summary = "Create new transaction",
            description = "Creates a transaction and sends it to Kafka",
            responses = {
                    @ApiResponse(
                            responseCode = "202",
                            description = "Transaction accepted",
                            content = @Content(schema = @Schema(implementation = TransactionResponse.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid request"),
                    @ApiResponse(responseCode = "500", description = "Internal error")
            }
    )
    @PostMapping
    ResponseEntity<TransactionResponse> createTransaction(@RequestBody TransactionRequest request);
}
