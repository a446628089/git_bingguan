package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByUsername(String username);
    Customer findByPhone(String phone);
    Customer findByIdCard(String idCard);
} 