package com.example.SimpleSpringApp.service;

import com.example.SimpleSpringApp.model.Client;

import java.util.List;

public interface ClientService {
    List<Client> findAll();
    Client findById(Long id);

    Client save(Client client);

    Client update(Client client);

    void deleteById(Long id);
}
