package org.kevinlin.springbootmall.controller;

import jakarta.validation.Valid;
import org.kevinlin.springbootmall.dto.createOrderRequest;
import org.kevinlin.springbootmall.model.Order;
import org.kevinlin.springbootmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<?> createOrder(@PathVariable Long userId,
                                         @RequestBody @Valid createOrderRequest createOrderRequest) {

        Long orderId = orderService.createOrder(userId, createOrderRequest);

        Order order = orderService.getOrderById(orderId);

        return ResponseEntity.status(HttpStatus.CREATED).body(order);


    }
}
