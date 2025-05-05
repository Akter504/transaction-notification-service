package ru.java.maryan.api.transactionnotificationservice.controllers.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.java.maryan.api.transactionnotificationservice.controllers.AuthController;
import ru.java.maryan.api.transactionnotificationservice.dto.request.LoginRequest;
import ru.java.maryan.api.transactionnotificationservice.dto.response.AccountResponse;
import ru.java.maryan.api.transactionnotificationservice.dto.response.LoginResponse;
import ru.java.maryan.api.transactionnotificationservice.dto.response.TokenResponse;
import ru.java.maryan.api.transactionnotificationservice.exceptions.AuthException;
import ru.java.maryan.api.transactionnotificationservice.models.Account;
import ru.java.maryan.api.transactionnotificationservice.models.User;
import ru.java.maryan.api.transactionnotificationservice.services.AccountService;
import ru.java.maryan.api.transactionnotificationservice.services.LoginService;
import ru.java.maryan.api.transactionnotificationservice.services.UserService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/api/auth")
public class AuthControllerImpl implements AuthController {
    private final UserService userService;
    private final LoginService loginService;
    private final AccountService accountService;

    @Autowired
    public AuthControllerImpl(UserService userService, LoginService loginService, AccountService accountService) {
        this.userService = userService;
        this.loginService = loginService;
        this.accountService = accountService;
    }

    @PostMapping("/login-by-email")
    public ResponseEntity<LoginResponse> loginByEmail(
            @Validated(LoginRequest.EmailGroup.class) @RequestBody LoginRequest request) {
        return processLogin(() -> userService.findUserByEmail(request.getEmail()),
                request.getPassword());
    }

    @PostMapping("/login-by-phone")
    public ResponseEntity<LoginResponse> loginByPhone(
            @Validated(LoginRequest.PhoneGroup.class) @RequestBody LoginRequest request) {
        return processLogin(() -> userService.findUserByPhoneNumber(request.getPhoneNumber()),
                request.getPassword());
    }

    private ResponseEntity<LoginResponse> processLogin(Supplier<Optional<User>> userSupplier,
                                                       String password) {
        User user = userSupplier.get()
                .orElseThrow(() -> new AuthException("Invalid credentials"));
        TokenResponse token = loginService.login(user, password);
        List<Account> accounts = accountService.findAccountsByUserId(user.getId());
        List<AccountResponse> accountsResponse = accounts == null
                ? Collections.emptyList()
                : convertToAccountResponse(accounts);
        LoginResponse response = LoginResponse.builder()
                .accounts(accountsResponse)
                .token(token)
                .build();
        return ResponseEntity.ok(response);
    }

    private List<AccountResponse> convertToAccountResponse(List<Account> accounts) {
        return accounts.stream()
                .map(account -> new AccountResponse(
                        account.getId(),
                        account.getBalance(),
                        account.getCurrency()
                ))
                .collect(Collectors.toList());
    }
}
