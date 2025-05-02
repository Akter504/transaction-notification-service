package ru.java.maryan.api.transactionnotificationservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.java.maryan.api.transactionnotificationservice.dto.request.LoginRequest;
import ru.java.maryan.api.transactionnotificationservice.dto.request.RegisterRequest;
import ru.java.maryan.api.transactionnotificationservice.dto.response.TokenResponse;
import ru.java.maryan.api.transactionnotificationservice.exceptions.AuthException;
import ru.java.maryan.api.transactionnotificationservice.models.User;
import ru.java.maryan.api.transactionnotificationservice.services.RegisterService;
import ru.java.maryan.api.transactionnotificationservice.services.UserService;

import java.util.Optional;

@Validated
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final RegisterService registerService;

    @Autowired
    public UserController(UserService userService, RegisterService registerService) {
        this.userService = userService;
        this.registerService = registerService;
    }

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> registerUser(@Validated @RequestBody RegisterRequest request) {
        Optional<User> existingUser = userService.findUserByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        TokenResponse token = registerService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(token);
    }
}
