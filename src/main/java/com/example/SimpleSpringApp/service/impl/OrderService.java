package com.example.SimpleSpringApp.service.impl;

import com.example.SimpleSpringApp.exception.EntityNotFoundException;
import com.example.SimpleSpringApp.exception.UpdateStateException;
import com.example.SimpleSpringApp.model.Order;
import com.example.SimpleSpringApp.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements com.example.SimpleSpringApp.service.OrderService {

    private final OrderRepository orderRepository;
    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(MessageFormat.format("Заказ с id {0} не найден!", id)));
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order update(Order order) {
        checkForUpdate(order.getId());
        return orderRepository.update(order);
    }

    @Override
    public void deleteById(Long id) {
        Order currentOrder = findById(id);
        currentOrder.getClient().removeOrder(id);
        orderRepository.deleteById(id);
    }

    @Override
    public void deleteByIdIn(List<Long> ids) {
        orderRepository.deleteByIdIn(ids);
    }

    private void checkForUpdate(Long orderId){
        Order currentOrder = findById(orderId);
        Instant now = Instant.now();

        Duration duration = Duration.between(currentOrder.getUpdateAt(), now);
        if(duration.getSeconds() > 5){
            throw new UpdateStateException("Невозможно обновить заказ!");
        }
    }
}
