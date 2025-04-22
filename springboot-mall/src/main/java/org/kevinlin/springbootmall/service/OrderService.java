package org.kevinlin.springbootmall.service;

import jakarta.validation.Valid;
import org.kevinlin.springbootmall.dto.createOrderRequest;

public interface OrderService {
    Long createOrder(Long userId, createOrderRequest createOrderRequest);
}
