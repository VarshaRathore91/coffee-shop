package com.order.coffeeshop.repository;

import com.order.coffeeshop.entity.ShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ShopRepository extends JpaRepository<ShopEntity, Integer> {
}