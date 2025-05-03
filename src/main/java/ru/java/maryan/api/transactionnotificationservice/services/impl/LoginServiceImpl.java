package ru.java.maryan.api.transactionnotificationservice.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.java.maryan.api.transactionnotificationservice.dto.response.TokenResponse;
import ru.java.maryan.api.transactionnotificationservice.exceptions.AuthException;
import ru.java.maryan.api.transactionnotificationservice.models.User;
import ru.java.maryan.api.transactionnotificationservice.services.LoginService;
import ru.java.maryan.api.transactionnotificationservice.utils.TokenUtils;

import java.util.Date;

@Service
public class LoginServiceImpl implements LoginService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenUtils tokenUtils;

    public LoginServiceImpl(BCryptPasswordEncoder passwordEncoder, TokenUtils tokenUtils) {
        this.passwordEncoder = passwordEncoder;
        this.tokenUtils = tokenUtils;
    }

    @Override
    public TokenResponse login(User user, String password) throws AuthException {
        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new AuthException("Invalid credentials");
        }
        String stringId = user.getId().toString();
        return new TokenResponse(tokenUtils.generateJwtToken(stringId));
    }
}
