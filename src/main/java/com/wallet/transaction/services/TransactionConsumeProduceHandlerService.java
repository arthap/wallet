package com.wallet.transaction.services;

import com.wallet.transaction.exception.InsufficientBalanceException;
import com.wallet.transaction.model.BalanceTransactionDto;
import com.wallet.transaction.model.TransactionStateType;
import com.wallet.transaction.model.TransactionType;
import com.wallet.transaction.model.WalletDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TransactionConsumeProduceHandlerService {

    private static final Logger log = LoggerFactory.getLogger(TransactionConsumeProduceHandlerService.class);

    @Autowired
    private WalletService walletService;

    @Autowired
    private BalanceHistoryService balanceHistoryService;

    @Autowired
    private TransactionSenderService transactionSenderService;


    @KafkaListener(topics = "transactions", containerFactory = "balanceTransactionKafkaListenerFactory")
    public BalanceTransactionDto consume(BalanceTransactionDto balance) {
        log.info("Consumed BalanceTransaction Message: " + balance);
        WalletDto savedWallet;
        try {
            if (balance.getTransactionType()== TransactionType.REPLENISH) {
                savedWallet = walletService.replenishBalance(balance.getWalletId(), balance.getAmount());
            } else {
                savedWallet = walletService.withdrawalBalance(balance.getWalletId(), balance.getAmount());
            }
            balance.setState(TransactionStateType.ACCEPTED);
            String walletTopicText = "The balance change transaction was successful. Current wallet "
                    + savedWallet.toString() + "with Success transaction:" + balance;
            transactionSenderService.sendWallet(walletTopicText);
        } catch (InsufficientBalanceException ex){
            balance.setState(TransactionStateType.REJECTED);
            log.error("An error occurred during the transaction. Message: " + ex.getMessage());
            String dlqTopicText = "The balance change transaction was failed! Failed transaction: "+ balance.toString();
            transactionSenderService.sendDlq(dlqTopicText);

        }
        balanceHistoryService.save(balance);
        return balance;
    }

}
