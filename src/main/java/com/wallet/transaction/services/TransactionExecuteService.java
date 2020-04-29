package com.wallet.transaction.services;


import com.wallet.transaction.model.BalanceTransactionDto;

import com.wallet.transaction.model.TransactionType;
import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TransactionExecuteService {
    @Autowired
    private WalletService walletService;

    @Autowired
    private TransactionSenderService transactionSenderService;

    @Autowired
    private Tasks tasks;

    public ExecutorService run() {

        ExecutorService es = Executors.newFixedThreadPool(5);

        for (int i = 1; i <= 10; i++) {
            es.execute(tasks::runReplenishTransaction);
            es.execute(tasks::runWithdrawalTransaction);
        }

        return es;
    }

    @Service
    @Transactional
    public class Tasks {
        public void runReplenishTransaction() {
            Random ran = new Random();
            int x = ran.nextInt(3000-1000) + 1000;
            walletService.gelAll().forEach(it ->
                    transactionSenderService.sendTransaction(
                            new BalanceTransactionDto(it.getId(),
                                    new BigDecimal(x),
                                    it.getBalance(),
                                    "new replenish transaction",
                                    TransactionType.REPLENISH)));
        }

        public void runWithdrawalTransaction() {
            Random ran = new Random();
            int w = ran.nextInt((10000 - 5000) + 1) + 4000;
            walletService.gelAll().forEach(it ->
                    transactionSenderService.sendTransaction(
                            new BalanceTransactionDto(it.getId(),
                                    new BigDecimal(w),
                                    it.getBalance(),
                                    "new withdrawal transaction",
                                    TransactionType.WITHDRAWAL)));
        }
    }
}
