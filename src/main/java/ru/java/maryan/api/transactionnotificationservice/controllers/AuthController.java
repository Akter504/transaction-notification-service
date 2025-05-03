package ru.java.maryan.api.transactionnotificationservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.java.maryan.api.transactionnotificationservice.dto.request.LoginRequest;
import ru.java.maryan.api.transactionnotificationservice.dto.response.TokenResponse;
import ru.java.maryan.api.transactionnotificationservice.exceptions.AuthException;
import ru.java.maryan.api.transactionnotificationservice.models.User;
import ru.java.maryan.api.transactionnotificationservice.services.LoginService;
import ru.java.maryan.api.transactionnotificationservice.services.UserService;

import java.util.Optional;
import java.util.function.Supplier;

@Validated
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final LoginService loginService;

    @Autowired
    public AuthController(UserService userService, LoginService loginService) {
        this.userService = userService;
        this.loginService = loginService;
    }

    @PostMapping("/login-by-email")
    public ResponseEntity<TokenResponse> loginByEmail(
            @Validated(LoginRequest.EmailGroup.class) @RequestBody LoginRequest request) {
        return processLogin(() -> userService.findUserByEmail(request.getEmail()),
                request.getPassword());
    }

    @PostMapping("/login-by-phone")
    public ResponseEntity<TokenResponse> loginByPhone(
            @Validated(LoginRequest.PhoneGroup.class) @RequestBody LoginRequest request) {
        return processLogin(() -> userService.findUserByPhoneNumber(request.getPhoneNumber()),
                request.getPassword());
    }

    private ResponseEntity<TokenResponse> processLogin(Supplier<Optional<User>> userSupplier,
                                                       String password) {
        User user = userSupplier.get()
                .orElseThrow(() -> new AuthException("Invalid credentials"));
        TokenResponse token = loginService.login(user, password);
        return ResponseEntity.ok(token);
    }
}
