package com.wallet.transaction.dao.domain;

import com.wallet.transaction.model.TransactionStateType;
import com.wallet.transaction.model.TransactionType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "balance_histories")
public class BalanceTransactionEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private UUID walletId;
    private LocalDateTime timestamp = LocalDateTime.now();
    private BigDecimal amount;
    private BigDecimal postBalance;
    private String description;
    @Column
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @Enumerated(EnumType.STRING)
    private TransactionStateType transactionStateType;

    public BalanceTransactionEntity() {
    }

    public BalanceTransactionEntity(UUID walletId, BigDecimal amount, BigDecimal postBalance, String description, TransactionType transactionType, TransactionStateType transactionStateType) {
        this.walletId = walletId;
        this.amount = amount;
        this.postBalance = postBalance;
        this.description = description;
        this.transactionType = transactionType;
        this.transactionStateType = transactionStateType;
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

    public TransactionStateType getTransactionStateType() {
        return transactionStateType;
    }

    public void setTransactionStateType(TransactionStateType transactionStateType) {
        this.transactionStateType = transactionStateType;
    }
}
