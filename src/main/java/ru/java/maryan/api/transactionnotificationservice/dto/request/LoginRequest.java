package ru.java.maryan.api.transactionnotificationservice.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    public interface EmailGroup {}
    public interface PhoneGroup {}

    @NotBlank(message = "The email address cannot be empty.", groups = EmailGroup.class)
    @Email(message = "Uncorrected email.", groups = EmailGroup.class)
    @Size(max = 40, groups = EmailGroup.class)
    private String email;

    @NotBlank(message = "The phone number cannot be empty.", groups = PhoneGroup.class)
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Uncorrected number.", groups = PhoneGroup.class)
    private String phoneNumber;

    @NotBlank(message = "The password cannot be empty.")
    private String password;
}
