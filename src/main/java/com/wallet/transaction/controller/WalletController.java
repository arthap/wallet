package com.wallet.transaction.controller;

import com.wallet.transaction.exception.ResourceNotFoundException;
import com.wallet.transaction.model.WalletDto;
import com.wallet.transaction.services.WalletService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WalletController {
    @Autowired
    private WalletService walletService;

    @GetMapping("/wallet/all")
    List<WalletDto> WalletServiceall() {
        return walletService.gelAll();
    }

    @GetMapping("/wallet/{id}")
    WalletDto getWalletById(@PathVariable UUID id) throws ResourceNotFoundException {
        return walletService.getById(id);
    }

    @PostMapping("/wallet")
    WalletDto save(@RequestBody WalletDto wallet) {
        return walletService.createWallet(wallet);
    }
}
