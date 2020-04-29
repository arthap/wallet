package com.wallet.transaction.model;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


public class BalanceTransactionDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID id;
    private UUID walletId;
    private LocalDateTime timestamp = LocalDateTime.now();
    private BigDecimal amount;
    private BigDecimal postBalance;
    private String description;
    private TransactionType transactionType;
    private TransactionStateType state;

    public BalanceTransactionDto() {
    }

    public BalanceTransactionDto(UUID walletId, BigDecimal amount, BigDecimal postBalance,
                                  String description, TransactionType transactionType) {
        this.walletId = walletId;
        this.amount = amount;
        this.postBalance = postBalance;
        this.description = description;
        this.transactionType = transactionType;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public void setWalletId(UUID walletId) {
        this.walletId = walletId;
    }


    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPostBalance() {
        return postBalance;
    }

    public void setPostBalance(BigDecimal postBalance) {
        this.postBalance = postBalance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public TransactionStateType getState() {
        return state;
    }

    public void setState(TransactionStateType state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "BalanceTransactionDto{" +
                "id=" + id +
                ", walletId=" + walletId +
                ", timestamp=" + timestamp +
                ", amount=" + amount +
                ", postBalance=" + postBalance +
                ", description='" + description + '\'' +
                ", transactionType=" + transactionType +
                ", state=" + state +
                '}';
    }
}
