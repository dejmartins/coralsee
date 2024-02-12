package com.coralpay.coralsee.dtos.responses.TransactionResponse;

import com.coralpay.coralsee.dtos.responses.TransactionResponse.TransactionResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class TransactionSummaryResponse {
    private String requestedAt;
    private List<TransactionResponse> transactionResponses;
}
