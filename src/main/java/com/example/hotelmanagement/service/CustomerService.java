package com.example.hotelmanagement.service;

import com.example.hotelmanagement.entity.Customer;
import com.example.hotelmanagement.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }

    public Customer findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    public Customer findByPhone(String phone) {
        return customerRepository.findByPhone(phone);
    }

    public Customer findByIdCard(String idCard) {
        return customerRepository.findByIdCard(idCard);
    }
} 