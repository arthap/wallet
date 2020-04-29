package com.wallet.transaction.services;

import com.wallet.transaction.model.ClientDto;
import com.wallet.transaction.model.ClientShortDto;
import java.util.List;
import java.util.UUID;

public interface ClientService {

    List<ClientDto> gelAll() throws RuntimeException;

    ClientDto getById(UUID id) throws RuntimeException;

    ClientDto createClient(ClientShortDto clientDto) throws RuntimeException;

    ClientDto updateClient(ClientDto clientDto, UUID id) throws RuntimeException;

    void deleteClient(UUID id) throws RuntimeException;
}
