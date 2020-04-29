package com.wallet.transaction.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

public class WalletDto implements Serializable {
    private static final long serialVersionUID = 4865903039190150223L;

    private UUID id;

    private UUID clientId;

    private BigDecimal balance;

    private String name;

    public WalletDto() {
    }

    public WalletDto(UUID id, UUID clientId, BigDecimal balance, String name) {
        this.id = id;
        this.clientId = clientId;
        this.balance = balance;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {

        this.clientId = clientId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "WalletDto{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", balance=" + balance +
                ", name='" + name + '\'' +
                '}';
    }
}

