package com.bogdan.shippingservice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "shipping_order")
public class ShippingOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String origin;

    private String destination;

    @Column(name = "placed_at")
    private LocalDateTime placeAt;
}


