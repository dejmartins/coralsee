package com.coralpay.coralsee.dtos.responses.TransactionResponse;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class TransactionResponse {
    private Long id;
    private String amount;
    private String paidAt;
    private String usdValue;
    private String emailAddress;
    private String gain;
    private String loss;
}
