package com.wallet.transaction.services.impl;


import com.wallet.transaction.dao.domain.WalletEntity;
import com.wallet.transaction.dao.repository.WalletRepository;
import com.wallet.transaction.exception.InsufficientBalanceException;
import com.wallet.transaction.exception.ResourceNotFoundException;
import com.wallet.transaction.mapper.ClientModelMapper;
import com.wallet.transaction.mapper.WalletModelMapper;
import com.wallet.transaction.model.WalletDto;
import com.wallet.transaction.services.ClientService;
import com.wallet.transaction.services.WalletService;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional/*(isolation = Isolation.SERIALIZABLE)*/
public class WalletServiceImpl implements WalletService {

    @Autowired
    WalletModelMapper walletModelMapper;

    @Autowired
    ClientModelMapper clientModelMapper;

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    ClientService clientService;

    @Override
    public List<WalletDto> gelAll() {
        return walletRepository.findAll().stream()
                .map(entity -> walletModelMapper.convertToDto(entity))
                .collect(Collectors.toList());
    }

    @Override
    public WalletDto getById(UUID id) throws ResourceNotFoundException {
        return walletRepository.findWalletById(id).map(entity -> walletModelMapper.convertToDto(entity))
                .orElseThrow(() -> new ResourceNotFoundException("Couldn't find a wallet by id: " + id));
    }

    @Override
    public WalletDto createWallet(WalletDto walletDto) {

        WalletEntity walletEntity = walletModelMapper.convertToDao(walletDto,
                clientModelMapper.convertToDao(clientService.getById(walletDto.getClientId())));
        return walletModelMapper.convertToDto(walletRepository.save(walletEntity));

    }

    @Override
    public void deleteWallet(UUID id) {
        walletRepository.deleteById(id);
    }

    @Override
    public WalletDto replenishBalance(UUID id, BigDecimal amount) throws ResourceNotFoundException {
        WalletEntity wallet = findById(id);
        wallet.setBalance(wallet.getBalance().add(amount));
        return walletModelMapper.convertToDto(walletRepository.save(wallet));
    }

    @Override
    public WalletDto withdrawalBalance(UUID id, BigDecimal amount) throws RuntimeException {

        WalletEntity wallet = findById(id);
        BigDecimal result = wallet.getBalance().subtract(amount);
        if (result.intValue() < 0) {
            throw new InsufficientBalanceException("Not enough balance in your wallet");
        }
        wallet.setBalance(wallet.getBalance().subtract(amount));
        return walletModelMapper.convertToDto(walletRepository.save(wallet));
    }

    private WalletEntity findById(UUID id) throws ResourceNotFoundException {
        return walletRepository.findWalletById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Couldn't find a wallet by id: " + id));
    }
}
