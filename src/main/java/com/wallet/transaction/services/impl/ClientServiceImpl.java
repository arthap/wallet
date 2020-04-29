package com.wallet.transaction.services.impl;


import com.wallet.transaction.dao.domain.ClientEntity;
import com.wallet.transaction.dao.repository.ClientRepository;
import com.wallet.transaction.exception.ResourceNotFoundException;
import com.wallet.transaction.mapper.ClientModelMapper;
import com.wallet.transaction.model.ClientDto;
import com.wallet.transaction.model.ClientShortDto;
import com.wallet.transaction.services.ClientService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(isolation = Isolation.SERIALIZABLE)
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientModelMapper clientModelMapper;

    @Autowired
    ClientRepository clientRepository;

    @Override
    public List<ClientDto> gelAll() {
        return clientRepository.findAll().stream()
                .map(entity -> clientModelMapper.convertToDto(entity))
                .collect(Collectors.toList());
    }

    @Override
    public ClientDto getById(UUID id) throws RuntimeException {
        return clientRepository.findById(id).map(entity -> clientModelMapper.convertToDto(entity))
                .orElseThrow(() -> new ResourceNotFoundException("Couldn't find a wallet by id: " + id));
    }

    @Override
    public ClientDto createClient(ClientShortDto clientDto) {
        ClientEntity clientEntity = clientModelMapper.convertToDaoFromShort(clientDto);
        return clientModelMapper.convertToDto(clientRepository.save(clientEntity));
    }

    @Override
    public void deleteClient(UUID id) {
        clientRepository.deleteById(id);
    }

    @Override
    public ClientDto updateClient(ClientDto clientDto, UUID id) throws RuntimeException {
        ClientEntity client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Couldn't find a wallet by id: " + id));
        client.setFirstName(clientDto.getFirstName());
        client.setFirstName(clientDto.getLastName());
        client.setFirstName(clientDto.getEmail());
        client.setFirstName(clientDto.getPhone());

        return clientModelMapper.convertToDto(clientRepository.save(client));
    }
}
