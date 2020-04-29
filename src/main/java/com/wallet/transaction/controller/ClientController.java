package com.wallet.transaction.controller;

import com.wallet.transaction.model.ClientDto;
import com.wallet.transaction.model.ClientShortDto;
import com.wallet.transaction.services.ClientService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {
    @Autowired
    private ClientService clientRepository;

    @GetMapping("/client/all")
    List<ClientDto> all() {
        return clientRepository.gelAll();
    }

    @GetMapping("/client/{id}")
    ClientDto getUserById(@PathVariable UUID id) {
        return clientRepository.getById(id);
    }

    @PostMapping("/client")
    ClientDto addClient(@RequestBody ClientShortDto client) {
        return clientRepository.createClient(client);
    }
}
