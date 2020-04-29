package com.wallet.transaction.model;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class ClientDto implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private UUID id;

    private String firstName;

    private String lastName;

    private String phone;

    private String email;

    private List<WalletDto> walletList;

    public ClientDto() {
        super();
    }

    public ClientDto(String firstName, String laastName, String email, String phone) {
        super();
        this.firstName = firstName;
        this.lastName = laastName;
        this.email = email;
        this.phone = phone;

    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<WalletDto> getWalletList() {
        return walletList;
    }

    public void setWalletList(List<WalletDto> walletList) {
        this.walletList = walletList;
    }
}
