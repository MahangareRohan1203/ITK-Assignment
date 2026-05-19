package com.itk.service;

import com.itk.entity.Wallet;
import com.itk.exception.InsufficientFundsException;
import com.itk.exception.WalletNotFoundException;
import com.itk.repository.WalletRepository;
import com.itk.request.PaymentRequest;
import com.itk.response.AccountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PaymentService implements IPaymentService {
    
    @Autowired
    private WalletRepository walletRepository;
    
    @Override
    @Transactional
    public AccountResponse performOperation(PaymentRequest paymentRequest) {
        Wallet wallet = walletRepository.findByIdForUpdate(paymentRequest.getWalletId()).orElseThrow(() -> new WalletNotFoundException("Wallet Not found for the walletId: " + paymentRequest.getWalletId()));
        
        switch (paymentRequest.getOperationType()) {
            case DEPOSIT -> {
                wallet.setBalance(wallet.getBalance().add(paymentRequest.getAmount()));
            }
            case WITHDRAW -> {
                if (wallet.getBalance().compareTo(paymentRequest.getAmount()) < 0)
                    throw new InsufficientFundsException("Your balance is low: " + wallet.getBalance());
                wallet.setBalance(wallet.getBalance().subtract(paymentRequest.getAmount()));
            }
        }
        walletRepository.save(wallet);
        
        return new AccountResponse(wallet.getId(), wallet.getBalance());
    }
    
    @Override
    @Transactional(readOnly = true)
    public AccountResponse getAccountInfo(UUID walletId) {
        Wallet wallet = walletRepository.findById(walletId).orElseThrow(() -> new WalletNotFoundException("Wallet Not found for the walletId: " + walletId));
        return new AccountResponse(wallet.getId(), wallet.getBalance());
    }
}
