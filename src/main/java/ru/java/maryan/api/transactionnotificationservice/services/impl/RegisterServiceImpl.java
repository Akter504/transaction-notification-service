package ru.java.maryan.api.transactionnotificationservice.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.java.maryan.api.transactionnotificationservice.dto.request.RegisterRequest;
import ru.java.maryan.api.transactionnotificationservice.dto.response.TokenResponse;
import ru.java.maryan.api.transactionnotificationservice.models.User;
import ru.java.maryan.api.transactionnotificationservice.services.RegisterService;
import ru.java.maryan.api.transactionnotificationservice.utils.TokenUtils;

import java.util.Date;

@Service
public class RegisterServiceImpl implements RegisterService {
    private final UserServiceImpl userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenUtils tokenUtils;

    public RegisterServiceImpl(UserServiceImpl userService, BCryptPasswordEncoder passwordEncoder, TokenUtils tokenUtils) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.tokenUtils = tokenUtils;
    }

    @Override
    public TokenResponse register(RegisterRequest request) {
        User user = new User();
        user.setSurnameUser(request.getSurnameUser());
        user.setNameUser(request.getNameUser());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        userService.createUser(user);

        String stringId = user.getId().toString();
        return new TokenResponse(tokenUtils.generateJwtToken(stringId));
    }
}
