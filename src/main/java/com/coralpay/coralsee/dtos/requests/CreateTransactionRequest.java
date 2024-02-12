package com.coralpay.coralsee.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateTransactionRequest {
    @NotBlank(message = "Amount cannot be empty")
    private String amountPaid;
    @Email(message = "Invalid Email Format")
    @NotBlank(message = "Email Address cannot be blank")
    private String emailAddress;
    private String paymentReference;
}
