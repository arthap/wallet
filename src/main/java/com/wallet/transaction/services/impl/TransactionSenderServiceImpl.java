package com.wallet.transaction.services.impl;

import com.wallet.transaction.model.BalanceTransactionDto;
import com.wallet.transaction.services.TransactionSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransactionSenderServiceImpl implements TransactionSenderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionSenderServiceImpl.class);

    @Autowired
    KafkaTemplate<String, BalanceTransactionDto> balanceTransactionKafkaTemplate;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.topic.transaction}")
    private String transactionsTopic;

    @Value("${kafka.topic.wallet}")
    private String walletTopic;

    @Value("${kafka.topic.dlq}")
    private String dlqTopic;

    public void sendTransaction(BalanceTransactionDto payload) {
        LOGGER.info("sending payload='{}'", payload);
        balanceTransactionKafkaTemplate.send(transactionsTopic, payload);
    }

    public void sendWallet(String payload) {
        LOGGER.info("sending payload='{}'", payload);
        kafkaTemplate.send(walletTopic, payload);
    }

    public void sendDlq(String payload) {
        LOGGER.info("sending payload='{}'", payload);
        kafkaTemplate.send(dlqTopic, payload);
    }
}
