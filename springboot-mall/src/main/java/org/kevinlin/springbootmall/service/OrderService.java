package org.kevinlin.springbootmall.service;

import jakarta.validation.Valid;
import org.kevinlin.springbootmall.dto.createOrderRequest;
import org.kevinlin.springbootmall.model.Order;

public interface OrderService {
    Long createOrder(Long userId, createOrderRequest createOrderRequest);

    Order getOrderById(Long orderId);
}
