package com.coralpay.coralsee.dtos.requests;

import com.coralpay.coralsee.annontations.ValidEmailAddress;
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
    @ValidEmailAddress
    @NotBlank(message = "Email cannot be blank")
    private String emailAddress;
}
