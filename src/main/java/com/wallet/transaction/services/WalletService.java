package com.wallet.transaction.services;

import com.wallet.transaction.model.WalletDto;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


public interface WalletService {

    List<WalletDto> gelAll() throws RuntimeException;

    WalletDto getById(UUID id) throws RuntimeException;

    WalletDto createWallet(WalletDto walletEntity) throws RuntimeException;

    void deleteWallet(UUID id) throws RuntimeException;

    WalletDto withdrawalBalance(UUID id, BigDecimal amount ) throws RuntimeException;

    WalletDto replenishBalance(UUID id, BigDecimal amount ) throws RuntimeException;


}
