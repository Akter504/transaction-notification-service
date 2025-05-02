package ru.java.maryan.api.transactionnotificationservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.java.maryan.api.transactionnotificationservice.dto.request.LoginRequest;
import ru.java.maryan.api.transactionnotificationservice.dto.response.LoginResponse;
import ru.java.maryan.api.transactionnotificationservice.models.User;
import ru.java.maryan.api.transactionnotificationservice.services.UserService;

import java.util.Optional;

@Validated
@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login-by-email")
    public ResponseEntity<LoginResponse> authorizationWithEmail(@Validated(LoginRequest.EmailGroup.class)
                                                                @RequestBody LoginRequest request) {
        return null;
    }

    @PostMapping("/login-by-phone")
    public ResponseEntity<LoginResponse> authorizationWithPhoneNumber(@Validated(LoginRequest.PhoneGroup.class)
                                                                      @RequestBody LoginRequest request) {
        return null;
    }
}
