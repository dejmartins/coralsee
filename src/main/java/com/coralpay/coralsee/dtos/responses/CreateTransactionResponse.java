package com.coralpay.coralsee.dtos.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CreateTransactionResponse {
    private Long id;
    private String amount;
    private String paidAt;
    private String usdValue;
    private String emailAddress;
}
