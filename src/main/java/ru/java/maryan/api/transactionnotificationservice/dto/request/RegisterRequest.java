package ru.java.maryan.api.transactionnotificationservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RegisterRequest {
    @NotBlank(message = "The email address cannot be empty.")
    @Email(message = "Uncorrected email.")
    @Size(max = 40)
    private String email;

    @NotBlank(message = "The phone number cannot be empty.")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Uncorrected number.")
    private String phoneNumber;

    @Size(max=20)
    private String nameUser;

    @Size(max=20)
    private String surnameUser;

    @NotBlank(message = "The password cannot be empty.")
    @Size(max=200, message = "The password can be max 200 symbols.")
    private String password;
}
