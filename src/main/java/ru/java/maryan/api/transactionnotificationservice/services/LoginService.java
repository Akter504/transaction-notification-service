package ru.java.maryan.api.transactionnotificationservice.services;

import ru.java.maryan.api.transactionnotificationservice.dto.response.TokenResponse;
import ru.java.maryan.api.transactionnotificationservice.exceptions.AuthException;
import ru.java.maryan.api.transactionnotificationservice.models.User;

public interface LoginService {
    TokenResponse login(User user, String password) throws AuthException;
}
