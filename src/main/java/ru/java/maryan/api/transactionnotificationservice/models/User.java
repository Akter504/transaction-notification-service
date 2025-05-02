package ru.java.maryan.api.transactionnotificationservice.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class User {
    private Long id;
    private String email;
    private String phoneNumber;
    private String nameUser;
    private String surnameUser;
    private String passwordHash;
    private LocalDateTime createdAt;
}
