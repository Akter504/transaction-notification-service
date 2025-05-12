package ru.java.maryan.api.transactionnotificationservice.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class User {
    private Long id;
    private String email;
    private String phoneNumber;
    private String nameUser;
    private String surnameUser;
    private String passwordHash;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    private LocalDateTime createdAt;
}
