package com.example.SimpleSpringApp.service.impl;

import com.example.SimpleSpringApp.exception.EntityNotFoundException;
import com.example.SimpleSpringApp.model.Client;
import com.example.SimpleSpringApp.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService implements com.example.SimpleSpringApp.service.ClientService {

    private final ClientRepository clientRepository;

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client findById(Long id) {
        return clientRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(MessageFormat.format("Клиент с id {0} не найден!", id)));
    }

    @Override
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Client update(Client client) {
        return clientRepository.update(client);
    }

    @Override
    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }
}
