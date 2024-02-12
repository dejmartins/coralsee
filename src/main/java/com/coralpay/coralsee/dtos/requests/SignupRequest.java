package com.coralpay.coralsee.dtos.requests;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignupRequest {
    private String fullName;
    private String password;
    private String phoneNumber;
    private String emailAddress;
}
