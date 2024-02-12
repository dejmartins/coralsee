package com.coralpay.coralsee.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignupRequest {
    @NotBlank(message = "Name cannot be blank")
    private String fullName;
    @NotBlank(message = "Password cannot be blank")
    private String password;
    @NotBlank(message = "Phone number cannot be blank")
    private String phoneNumber;
    @Email(message = "Invalid Email Format")
    @NotBlank(message = "Email cannot be blank")
    private String emailAddress;
}
