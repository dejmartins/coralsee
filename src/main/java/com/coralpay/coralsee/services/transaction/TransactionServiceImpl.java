package com.coralpay.coralsee.services.transaction;

import com.coralpay.coralsee.dtos.requests.CreateTransactionRequest;
import com.coralpay.coralsee.dtos.responses.CreateTransactionResponse;
import com.coralpay.coralsee.dtos.responses.UserResponse;
import com.coralpay.coralsee.entities.Transaction;
import com.coralpay.coralsee.entities.User;
import com.coralpay.coralsee.exceptions.TransactionException.InvalidTransactionDetailException;
import com.coralpay.coralsee.repositories.TransactionRepository;
import com.coralpay.coralsee.services.user_management_service.UserService;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.coralpay.coralsee.validators.TransactionValidator.validateCreateTransactionRequest;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService{

    @Value("${exchange.rate.apu}")
    private String exhangeRateApi;
    public final ModelMapper mapper;
    public final UserService userService;
    public final TransactionRepository transactionRepository;

    @Override
    public CreateTransactionResponse createTransaction(CreateTransactionRequest transactionRequest) throws InvalidTransactionDetailException, IOException {
        validateCreateTransactionRequest(transactionRequest);

        Transaction transaction = new Transaction();
        transaction.setAmountPaid(transactionRequest.getAmountPaid());
        transaction.setUser(getUser(transactionRequest.getEmailAddress()));
        transaction.setPaidAt(LocalDateTime.now());
        transaction.setUsdValue(getUsdValue());

        System.out.println((getUsdValue()));

        Transaction savedTransaction = transactionRepository.save(transaction);

        return getTransactionResponseOf(savedTransaction);
    }

    private String getUsdValue() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(exhangeRateApi)
                .build();

        Response response = client.newCall(request).execute();

        String responseBody = response.body().string();

        JSONObject jsonResponseBody = new JSONObject(responseBody);

        if (!response.isSuccessful()) {
            throw new IOException(jsonResponseBody.getString("responseMessage"));
        }

        String conversionRates = jsonResponseBody.get("conversion_rates").toString();

        JSONObject jsonConversionRates = new JSONObject(conversionRates);

        return jsonConversionRates.get("NGN").toString();
    }

    private CreateTransactionResponse getTransactionResponseOf(Transaction transaction){
        CreateTransactionResponse response = CreateTransactionResponse.builder()
                .id(transaction.getId())
                .paidAt(transaction.getPaidAt().format(DateTimeFormatter.ISO_DATE_TIME))
                .amount(transaction.getAmountPaid())
                .emailAddress(transaction.getUser().getEmailAddress())
                .usdValue(transaction.getUsdValue())
                .build();

        return response;
    }

    private User getUser(String emailAddress) {
        UserResponse user = userService.getUserBy(emailAddress);
        return mapper.map(user, User.class);
    }
}
