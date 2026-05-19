package com.itk.request;

import com.itk.entity.OperationType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PaymentRequest {
    
    @NotNull(message = "walletId cannot be null/empty")
    private UUID walletId;
    
    
    @NotNull(message = "amount cannot be null/empty")
    @Positive(message = "amount should be positive")
    private BigDecimal amount;
    
    @NotNull(message = "operationType should be DEPOSIT/WITHDRAW")
    private OperationType operationType;
}


