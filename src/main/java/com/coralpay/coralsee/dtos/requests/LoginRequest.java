package com.coralpay.coralsee.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@NoArgsConstructor
public class LoginRequest {
    @Email(message = "Invalid Email Format")
    @NotBlank(message = "Email cannot be blank")
    private String emailAddress;
    @NotBlank(message = "Password cannot be blank")
    private String password;
}
