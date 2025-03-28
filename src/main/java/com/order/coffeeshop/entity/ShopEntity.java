package com.order.coffeeshop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "shop")
public class ShopEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;
    private String location;
    private String contactNumber;
    private String menu;
    private int queueCount;
    private int queueSize;
    private java.time.LocalTime openingTime;
    private java.time.LocalTime closingTime;
//    private double latitude;
//    private double longitude;
//    private double accuracy;
//    private String locationSource;
//    private LocalDateTime lastUpdated;
}