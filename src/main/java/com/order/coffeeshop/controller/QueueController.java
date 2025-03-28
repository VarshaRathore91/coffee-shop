package com.order.coffeeshop.controller;

import com.order.coffeeshop.dto.QueuePositionResponse;
import com.order.coffeeshop.entity.ShopEntity;
import com.order.coffeeshop.repository.OrderRepository;
import com.order.coffeeshop.repository.ShopRepository;
import com.order.coffeeshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/queue")
public class QueueController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ShopRepository shopRepository;

    @GetMapping("/position/{customerId}/{shopId}")
    public QueuePositionResponse getQueuePosition(@PathVariable int customerId, @PathVariable int shopId) {
        int position = orderService.countByShopIdAndStatusOrderByCreatedAt(shopId, "PENDING", customerId);
        ShopEntity shop = shopRepository.findById(shopId).orElseThrow(() -> new RuntimeException("Shop not found"));

        int avgWaitTimePerOrder = 2; // Assume 2 minutes per coffee order
        int estimatedWaitTime = position * avgWaitTimePerOrder;

        return new QueuePositionResponse(position, estimatedWaitTime);
    }
    @DeleteMapping("/exit/{customerId}/{shopId}")
    public ResponseEntity<Map<String, String>> cancelOrder(@PathVariable int customerId, @PathVariable int shopId) {
        orderService.cancelOrder(customerId, shopId);
        return ResponseEntity.ok(Map.of("message", "Customer has exited the queue and the order has been canceled."));
    }
}