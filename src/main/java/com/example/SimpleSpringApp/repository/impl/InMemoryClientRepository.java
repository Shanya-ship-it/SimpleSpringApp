package com.example.SimpleSpringApp.repository.impl;

import com.example.SimpleSpringApp.exception.EntityNotFoundException;
import com.example.SimpleSpringApp.model.Client;
import com.example.SimpleSpringApp.model.Order;
import com.example.SimpleSpringApp.repository.ClientRepository;
import com.example.SimpleSpringApp.repository.OrderRepository;
import com.example.SimpleSpringApp.utils.BeanUtils;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class InMemoryClientRepository implements ClientRepository {

    private OrderRepository orderRepository;

    private final Map<Long, Client> repository = new ConcurrentHashMap<>();

    private final AtomicLong currentId = new AtomicLong(1);



    @Override
    public List<Client> findAll() {
        return new ArrayList<>(repository.values());
    }

    @Override
    public Optional<Client> findById(Long id) {
        return Optional.ofNullable(repository.get(id));
    }

    @Override
    public Client save(Client client) {
        Long clientId = currentId.getAndIncrement();
        client.setId(clientId);
        repository.put(clientId, client);
        return client;
    }

    @Override
    public Client update(Client client) {
        Long clientId = client.getId();
        Client currentClient = repository.get(clientId);
        if(currentClient == null) {
            throw new EntityNotFoundException(MessageFormat.format("Клиент по ID {0} не найден!", clientId));
        }
        BeanUtils.copyNonNullProperties(client, currentClient);
        currentClient.setId(clientId);

        repository.put(clientId, currentClient);
        return currentClient;
    }

    @Override
    public void deleteById(Long id) {
        Client client = repository.get(id);
        if(client == null){
            throw new EntityNotFoundException(MessageFormat.format("Клиент по ID {0} не найден!", id));
        }

        orderRepository.deleteByIdIn(client.getOrders().stream().map(Order::getId).collect(Collectors.toList()));
        repository.remove(id);
    }

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
}
