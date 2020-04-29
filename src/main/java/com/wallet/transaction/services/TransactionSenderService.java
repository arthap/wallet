package com.wallet.transaction.services;

import com.wallet.transaction.model.BalanceTransactionDto;
import org.springframework.stereotype.Service;

@Service
public interface TransactionSenderService {

    void sendTransaction(BalanceTransactionDto payload);

    void sendWallet(String payload);

    void sendDlq(String payload);

}
