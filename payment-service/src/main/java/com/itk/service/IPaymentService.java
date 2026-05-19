package com.itk.service;

import com.itk.request.PaymentRequest;
import com.itk.response.AccountResponse;

import java.util.UUID;

public interface IPaymentService {
    public AccountResponse performOperation(PaymentRequest paymentRequest);
    public AccountResponse getAccountInfo(UUID walletId);
}
