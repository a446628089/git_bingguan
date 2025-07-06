package com.example.hotelmanagement.service;

import com.example.hotelmanagement.entity.Customer;
import com.example.hotelmanagement.entity.Employee;
import com.example.hotelmanagement.repository.CustomerRepository;
import com.example.hotelmanagement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByUsername(username);
        if (employee != null) {
            GrantedAuthority authority = new SimpleGrantedAuthority(employee.getRole());
            return new User(employee.getUsername(), employee.getPassword(), Collections.singleton(authority));
        }
        Customer customer = customerRepository.findByUsername(username);
        if (customer != null) {
            GrantedAuthority authority = new SimpleGrantedAuthority("CUSTOMER");
            return new User(customer.getUsername(), customer.getPassword(), Collections.singleton(authority));
        }
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
} 