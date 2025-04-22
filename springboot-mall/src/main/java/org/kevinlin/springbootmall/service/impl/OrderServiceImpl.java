package org.kevinlin.springbootmall.service.impl;

import org.kevinlin.springbootmall.dao.OrderDao;
import org.kevinlin.springbootmall.dao.ProductDao;
import org.kevinlin.springbootmall.dto.BuyItem;
import org.kevinlin.springbootmall.dto.createOrderRequest;
import org.kevinlin.springbootmall.model.Order;
import org.kevinlin.springbootmall.model.OrderItem;
import org.kevinlin.springbootmall.model.Product;
import org.kevinlin.springbootmall.service.OrderService;
import org.kevinlin.springbootmall.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private ProductDao productDao;

    @Override
    public Order getOrderById(Long orderId) {
        Order order = orderDao.getOrderById(orderId);

        List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(orderId);

        order.setOrderItemList(orderItemList);

        return order;
    }

    @Transactional
    @Override
    public Long createOrder(Long userId, createOrderRequest createOrderRequest) {
        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        for(BuyItem buyItem : createOrderRequest.getBuyItemsList()) {
            Product product = productDao.getProductById(buyItem.getProductId());

            //計算訂單總花費
            int amount = product.getPrice() * buyItem.getQuantity();
            totalAmount += amount;

            //轉換BuyItem為OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderItemId(Long.parseLong(idGenerator.generateId()));
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);

            orderItemList.add(orderItem);
        }


        Order order = new Order();
        order.setOrderId(Long.parseLong(idGenerator.generateId()));
        order.setUserId(userId);
        order.setTotalAmount(totalAmount);

        orderDao.createOrderItems(order.getOrderId(),orderItemList);


        return orderDao.createOrder(order);
    }
}
