package com.wallet.transaction.mapper;

import com.wallet.transaction.dao.domain.BalanceTransactionEntity;
import com.wallet.transaction.model.BalanceTransactionDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BalanceTransactionModelMapper {

    ModelMapper modelMapper = new ModelMapper();

    public BalanceTransactionDto convertToDto(BalanceTransactionEntity balanceTransactionEntity) {
        BalanceTransactionDto balanceTransactionDto = modelMapper.map(balanceTransactionEntity, BalanceTransactionDto.class);
        return balanceTransactionDto;
    }

    public BalanceTransactionEntity convertToDao(BalanceTransactionDto balanceTransactionDto) {
        BalanceTransactionEntity balanceTransactionEntity = modelMapper.map(balanceTransactionDto, BalanceTransactionEntity.class);
        return balanceTransactionEntity;
    }
}
