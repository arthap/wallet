package com.wallet.transaction.services.impl;

import com.wallet.transaction.dao.domain.BalanceTransactionEntity;
import com.wallet.transaction.dao.repository.BalanceHistoryTansactionRepository;
import com.wallet.transaction.exception.ResourceNotFoundException;
import com.wallet.transaction.mapper.BalanceTransactionModelMapper;
import com.wallet.transaction.model.BalanceTransactionDto;
import com.wallet.transaction.services.BalanceHistoryService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BalanceHistoryServiceImpl implements BalanceHistoryService {

    @Autowired
    BalanceHistoryTansactionRepository balanceHistoryTansactionRepository;

    @Autowired
    BalanceTransactionModelMapper balanceTransactionModelMapper;

    @Override
    public List<BalanceTransactionDto> gelAll() {
        return balanceHistoryTansactionRepository.findAll().stream()
                .map(entity -> balanceTransactionModelMapper.convertToDto(entity))
                .collect(Collectors.toList());
    }

    @Override
    public BalanceTransactionDto getById(UUID id) throws RuntimeException {
        return balanceHistoryTansactionRepository.findById(id).map(entity -> balanceTransactionModelMapper.convertToDto(entity))
                .orElseThrow(() -> new ResourceNotFoundException("Couldn't find a wallet by id: " + id));
    }

    @Override
    public BalanceTransactionDto save(BalanceTransactionDto balanceTransactionDto) {
        BalanceTransactionEntity balanceTransactionEntity = balanceTransactionModelMapper.convertToDao(balanceTransactionDto);
        return balanceTransactionModelMapper.convertToDto(balanceHistoryTansactionRepository.save(balanceTransactionEntity));
    }
}
