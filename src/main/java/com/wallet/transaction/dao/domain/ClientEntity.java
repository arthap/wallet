package com.wallet.transaction.dao.domain;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "clients")
public class ClientEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String firstName;

    private String lastName;

    @Column(unique=true)
    private String phone;

    @Column(unique=true)
    private String email;

    @Column(name = "createdTime")
    private LocalDateTime timestamp = LocalDateTime.now();

    public List<WalletEntity> getWalletList() {
        return walletList;
    }

    public void setWalletList(List<WalletEntity> walletList) {
        this.walletList = walletList;
    }

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private List<WalletEntity> walletList;

    public ClientEntity() {
        super();
    }

    public ClientEntity(String firstName, String laastName, String email, String phone) {
        super();
        this.firstName = firstName;
        this.lastName = laastName;
        this.email = email;
        this.phone = phone;

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

    public void setLastName(String laastName) {
        this.lastName = laastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
