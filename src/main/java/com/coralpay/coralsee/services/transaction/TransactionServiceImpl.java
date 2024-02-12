package com.coralpay.coralsee.services.transaction;

import com.coralpay.coralsee.dtos.requests.CreateTransactionRequest;
import com.coralpay.coralsee.dtos.responses.TransactionResponse.TransactionResponse;
import com.coralpay.coralsee.dtos.responses.TransactionResponse.TransactionSummaryResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static com.coralpay.coralsee.validators.TransactionValidator.validateCreateTransactionRequest;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService{

    @Value("${exchange.rate.api}")
    private String exhangeRateApi;
    public final ModelMapper mapper;
    public final UserService userService;
    public final TransactionRepository transactionRepository;

    @Override
    public TransactionResponse createTransaction(CreateTransactionRequest transactionRequest) throws InvalidTransactionDetailException, IOException {
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

    @Override
    public TransactionSummaryResponse getWeekToDateSummary(String userId, int number, int size) {
        Pageable pageable = PageRequest.of(number, size, Sort.Direction.DESC, "paidAt");

        LocalDateTime startDate = LocalDateTime.now().minusWeeks(1);
        Page<Transaction> transactionPage = transactionRepository.findWeekToDateTransactions(userId, startDate, pageable);

        List<TransactionResponse> responses = transactionPage.getContent()
                .stream().map(this::getTransactionResponseOf).toList();

        TransactionSummaryResponse transactions = TransactionSummaryResponse.builder()
                .requestedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                .transactionResponses(responses)
                .build();

        return transactions;
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

    private TransactionResponse getTransactionResponseOf(Transaction transaction){
        TransactionResponse response = TransactionResponse.builder()
                .id(transaction.getId())
                .paidAt(transaction.getPaidAt().format(DateTimeFormatter.ISO_DATE_TIME))
                .amount(transaction.getAmountPaid())
                .emailAddress(transaction.getUser().getEmailAddress())
                .usdValue(transaction.getUsdValue())
                .gain(BigDecimal.ZERO.toString())
                .loss(BigDecimal.ZERO.toString())
                .build();

        return response;
    }

    private TransactionResponse getTransactionResponseWithGainOrLoss(Transaction transaction) throws IOException {
        String currentUsdValue = getUsdValue();
        BigDecimal difference = BigDecimal.valueOf(Long.parseLong(currentUsdValue)).subtract(BigDecimal.valueOf(Long.parseLong(transaction.getUsdValue())));

        TransactionResponse response = TransactionResponse.builder()
                .id(transaction.getId())
                .paidAt(transaction.getPaidAt().format(DateTimeFormatter.ISO_DATE_TIME))
                .amount(transaction.getAmountPaid())
                .emailAddress(transaction.getUser().getEmailAddress())
                .usdValue(transaction.getUsdValue())
                .gain(BigDecimal.ZERO.toString())
                .loss(BigDecimal.ZERO.toString())
                .build();

        if(difference.compareTo(BigDecimal.ZERO) > 0){
            response.setGain(difference.toString());
        } else {
            response.setLoss(difference.toString());
        }

        return response;
    }

    private User getUser(String emailAddress) {
        UserResponse user = userService.getUserBy(emailAddress);
        return mapper.map(user, User.class);
    }
}
