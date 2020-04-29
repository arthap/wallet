package com.wallet.transaction.mapper;

import com.wallet.transaction.dao.domain.ClientEntity;
import com.wallet.transaction.dao.domain.WalletEntity;
import com.wallet.transaction.model.WalletDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class WalletModelMapper {

    ModelMapper modelMapper = new ModelMapper();

    public WalletDto convertToDto(WalletEntity walletEntity) {
        WalletDto walletDto = modelMapper.map(walletEntity, WalletDto.class);
        walletDto.setClientId(walletEntity.getClient().getId());

        return walletDto;
    }

    public WalletEntity convertToDao(WalletDto walletDto, ClientEntity clientEntity) {
        WalletEntity walletEntity = modelMapper.map(walletDto, WalletEntity.class);
        walletEntity.setClient(clientEntity);
        return walletEntity;
    }
}
