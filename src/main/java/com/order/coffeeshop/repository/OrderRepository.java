package com.order.coffeeshop.repository;

import com.order.coffeeshop.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderEntity,     Integer> {

    @Query("SELECT COUNT(o) FROM OrderEntity o WHERE o.shopId = :shopId AND o.status = :status AND o.customerId = :customerId")
    int countByShopIdAndStatusOrderByCreatedAt(int shopId, String status, int customerId);

    void deleteByCustomerIdAndShopId(int customerId, int shopId);

    Optional<OrderEntity> findByCustomerIdAndShopId(int customerId, int shopId);

}