package com.order.coffeeshop.service;


import com.order.coffeeshop.dto.OrderRequest;
import com.order.coffeeshop.entity.OrderEntity;
import com.order.coffeeshop.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;


@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    @Transactional
    public OrderEntity placeOrder(OrderRequest request) {
        OrderEntity order = OrderEntity.builder()
                .customerId(request.getCustomerId())
                .shopId(request.getShopId())
                .coffeeType(request.getCoffeeType())
                .quantity(request.getQuantity())
                .status("PENDING")
                .build();
        return orderRepository.save(order);
    }

    @Transactional
    public void cancelOrder(int customerId, int shopId) {
        Optional<OrderEntity> order = orderRepository.findByCustomerIdAndShopId(customerId, shopId);

        if (order.isPresent()) {
            orderRepository.delete(order.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found for customer.");
        }
    }

public int countByShopIdAndStatusOrderByCreatedAt(int shopId, String status, int customerId){
      return  orderRepository.countByShopIdAndStatusOrderByCreatedAt(shopId, status, customerId);
}
}