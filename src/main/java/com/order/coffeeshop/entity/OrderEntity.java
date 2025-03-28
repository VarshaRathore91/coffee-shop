package com.order.coffeeshop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private int customerId;

    @Column(nullable = false)
    private int shopId;

    @Column(nullable = false)
    private String coffeeType;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private String status;
}