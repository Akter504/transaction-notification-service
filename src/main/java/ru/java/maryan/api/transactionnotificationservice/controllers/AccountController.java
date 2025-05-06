package ru.java.maryan.api.transactionnotificationservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import ru.java.maryan.api.transactionnotificationservice.dto.request.AccountRequest;
import ru.java.maryan.api.transactionnotificationservice.dto.response.AccountResponse;

@Tag(
        name = "Account Controller",
        description = "Endpoints for account management"
)
public interface AccountController {
    @Operation(
            summary = "Create account",
            description = "Creates a new account for the user",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Account successfully created",
                            content = @Content(schema = @Schema(implementation = AccountResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content
                    )
            }
    )
    @PostMapping
    ResponseEntity<AccountResponse> createAccount(@RequestBody AccountRequest request);
}
