package com.wallet.transaction.validator;

import com.wallet.transaction.exception.IllegalArgumentBalanceException;
import com.wallet.transaction.model.BalanceTransactionDto;
import org.springframework.stereotype.Component;

@Component
public class RequestValidator {
    public void validateRequest(final BalanceTransactionDto balanceTransactionDto) {

        if (balanceTransactionDto.getAmount() == null) {
            throw new IllegalArgumentBalanceException("Amount can't be null");
        }
        if (balanceTransactionDto.getWalletId() == null) {
            throw new IllegalArgumentBalanceException("Wallet id can't be null");
        }
        if (balanceTransactionDto.getTransactionType() == null) {
            throw new IllegalArgumentBalanceException("Transaction type can't be null");
        }
    }
}
