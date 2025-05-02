package ru.java.maryan.api.transactionnotificationservice.services;

import ru.java.maryan.api.transactionnotificationservice.dto.request.RegisterRequest;
import ru.java.maryan.api.transactionnotificationservice.dto.response.TokenResponse;

public interface RegisterService {
    TokenResponse register(RegisterRequest id);
}
