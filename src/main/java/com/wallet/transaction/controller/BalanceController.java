package com.wallet.transaction.controller;


import com.wallet.transaction.exception.ResourceNotFoundException;
import com.wallet.transaction.model.BalanceTransactionDto;
import com.wallet.transaction.services.TransactionSenderService;
import com.wallet.transaction.services.WalletService;
import com.wallet.transaction.validator.RequestValidator;
import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BalanceController {
    @Autowired
    private WalletService walletService;
    @Autowired
    RequestValidator requestValidator;

    @Autowired
    private TransactionSenderService transactionSenderService;

    @GetMapping("/balance/{walletId}")
    BigDecimal getWalletById(@PathVariable UUID walletId) throws ResourceNotFoundException {
        return walletService.getById(walletId).getBalance();
    }

    @PutMapping("/balance/change/{walletId}")
    void withdrawalBalance(@PathVariable UUID walletId, @RequestBody BalanceTransactionDto balance) {
        balance.setWalletId(walletId);
        requestValidator.validateRequest(balance);
        transactionSenderService.sendTransaction(balance);
    }
}
