package com.example.hotelmanagement.entity;

import javax.persistence.*;
import lombok.Data;

@Entity
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    private String name;

    private String phone;

    private String email;

    @Column(nullable = false, unique = true)
    private String idCard;

    public Customer() {}
} 