package com.wallet.transaction.mapper;

import com.wallet.transaction.dao.domain.ClientEntity;
import com.wallet.transaction.model.ClientDto;
import com.wallet.transaction.model.ClientShortDto;
import java.util.Collections;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ClientModelMapper {

    ModelMapper modelMapper = new ModelMapper();

    public ClientDto convertToDto(ClientEntity clientEntity) {
        ClientDto clientDto = modelMapper.map(clientEntity, ClientDto.class);
        return clientDto;
    }

    public ClientEntity convertToDaoFromShort(ClientShortDto clientDto) {
        ClientEntity clientEntity = modelMapper.map(clientDto, ClientEntity.class);
        clientEntity.setWalletList(Collections.emptyList());
        return clientEntity;
    }

    public ClientEntity convertToDao(ClientDto clientDto) {
        ClientEntity clientEntity = modelMapper.map(clientDto, ClientEntity.class);
        clientEntity.setWalletList(Collections.emptyList());
        return clientEntity;
    }
}
