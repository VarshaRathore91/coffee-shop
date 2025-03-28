package com.order.coffeeshop.service;

import com.order.coffeeshop.dto.OrderRequest;
import com.order.coffeeshop.entity.OrderEntity;
import com.order.coffeeshop.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private int customerId;
    private int shopId;
    private OrderRequest orderRequest;
    private Random random;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        random = new Random();  // Initialize Random
        customerId = random.nextInt();
        shopId = random.nextInt();
        orderRequest = new OrderRequest(customerId, shopId, "Espresso", 2);
    }

    @Test
    void placeOrder_success() {
        OrderEntity orderEntity = OrderEntity.builder()
                .customerId(customerId)
                .shopId(shopId)
                .coffeeType("Espresso")
                .quantity(2)
                .status("PENDING")
                .build();

        when(orderRepository.save(any(OrderEntity.class))).thenReturn(orderEntity);

        OrderEntity savedOrder = orderService.placeOrder(orderRequest);

        assertNotNull(savedOrder);
        assertEquals("Espresso", savedOrder.getCoffeeType());
        assertEquals(2, savedOrder.getQuantity());
        assertEquals("PENDING", savedOrder.getStatus());
    }

    @Test
    void cancelOrder_orderExists() {
        OrderEntity existingOrder = OrderEntity.builder()
                .customerId(customerId)
                .shopId(shopId)
                .coffeeType("Espresso")
                .quantity(2)
                .status("PENDING")
                .build();

        when(orderRepository.findByCustomerIdAndShopId(customerId, shopId)).thenReturn(Optional.of(existingOrder));

        orderService.cancelOrder(customerId, shopId);

        verify(orderRepository, times(1)).delete(existingOrder);
    }

    @Test
    void cancelOrder_orderNotFound() {
        when(orderRepository.findByCustomerIdAndShopId(customerId, shopId)).thenReturn(Optional.empty());

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () ->
                orderService.cancelOrder(customerId, shopId)
        );

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals("Order not found for customer.", thrown.getReason());
    }

    @Test
    void countByShopIdAndStatusOrderByCreatedAt() {
        when(orderRepository.countByShopIdAndStatusOrderByCreatedAt(shopId, "PENDING", customerId)).thenReturn(5);

        int count = orderService.countByShopIdAndStatusOrderByCreatedAt(shopId, "PENDING", customerId);

        assertEquals(5, count);
    }
}
