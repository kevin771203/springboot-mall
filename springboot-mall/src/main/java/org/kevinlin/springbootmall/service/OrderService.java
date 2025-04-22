package org.kevinlin.springbootmall.service;

import jakarta.validation.Valid;
import org.kevinlin.springbootmall.dto.OrderQueryParams;
import org.kevinlin.springbootmall.dto.createOrderRequest;
import org.kevinlin.springbootmall.model.Order;

import java.util.List;

public interface OrderService {

    Long createOrder(Long userId, createOrderRequest createOrderRequest);

    Order getOrderById(Long orderId);

    List<Order> getOrders(OrderQueryParams orderQueryParams);

    Integer countOrders(OrderQueryParams orderQueryParams);
}
