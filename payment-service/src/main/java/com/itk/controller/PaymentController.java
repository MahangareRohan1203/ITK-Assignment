package com.itk.controller;

import com.itk.request.PaymentRequest;
import com.itk.response.AccountResponse;
import com.itk.service.IPaymentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class PaymentController {
    
    @Autowired
    private IPaymentService paymentService;
    
    @PostMapping("/wallet")
    public ResponseEntity<?> performOperation(@Valid @RequestBody PaymentRequest paymentRequest) {
        log.info("Received the change balance request for: UUID:{} ,operationType:{},  amount: {}", paymentRequest.getWalletId(), paymentRequest.getOperationType(), paymentRequest.getAmount());
        AccountResponse accountResponse = paymentService.performOperation(paymentRequest);
        
        return new ResponseEntity<>(accountResponse, HttpStatus.CREATED);
    }
    
    @GetMapping("/wallets/{wallet_uuid}")
    public ResponseEntity<?> getWalletBalance(@PathVariable("wallet_uuid") UUID walletId) {
        log.info("Received the getWalletBalance Request for UUID: {}", walletId);
        AccountResponse accountResponse = paymentService.getAccountInfo(walletId);
        return new ResponseEntity<>(accountResponse, HttpStatus.OK);
    }
    
    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return new ResponseEntity<>("Hello Java", HttpStatus.OK);
    }
}
