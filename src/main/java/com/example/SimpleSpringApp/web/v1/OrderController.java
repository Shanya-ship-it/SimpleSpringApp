package com.example.SimpleSpringApp.web.v1;

import com.example.SimpleSpringApp.mapper.v1.OrderMapper;
import com.example.SimpleSpringApp.model.Order;
import com.example.SimpleSpringApp.service.impl.OrderService;
import com.example.SimpleSpringApp.web.model.OrderListResponse;
import com.example.SimpleSpringApp.web.model.OrderResponse;
import com.example.SimpleSpringApp.web.model.UpsertOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @GetMapping
    public ResponseEntity<OrderListResponse> findAll(){
        return ResponseEntity.ok(orderMapper.orderListToOrderListResponse(orderService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(
                orderMapper.orderToResponse(orderService.findById(id))
        );
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(@RequestBody UpsertOrderRequest request){
        Order newOrder = orderService.save(orderMapper.requestToOrder(request));

        return ResponseEntity.status(HttpStatus.CREATED).body(orderMapper.orderToResponse(newOrder));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> update(@PathVariable ("id") Long orderId, @RequestBody UpsertOrderRequest request){
        Order updatedOrder = orderService.update(orderMapper.requestToOrder(orderId, request));

        return ResponseEntity.ok(orderMapper.orderToResponse(updatedOrder));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        orderService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
