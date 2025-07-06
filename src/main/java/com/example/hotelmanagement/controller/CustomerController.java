package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.entity.Customer;
import com.example.hotelmanagement.service.CustomerService;
import com.example.hotelmanagement.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoomService roomService;

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Customer> getCustomerById(@PathVariable Long id) {
        return customerService.findById(id);
    }

    @PostMapping("/register")
    public Customer register(@RequestBody Customer customer) {
        if (customer.getIdCard() == null || customer.getIdCard().isEmpty()) {
            throw new RuntimeException("身份证号不能为空");
        }
        return customerService.save(customer);
    }

    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        if (customer.getIdCard() == null || customer.getIdCard().isEmpty()) {
            throw new RuntimeException("身份证号不能为空");
        }
        // 检查唯一性
        Customer existByIdCard = customerService.findByIdCard(customer.getIdCard());
        if (existByIdCard != null && !existByIdCard.getId().equals(id)) {
            throw new RuntimeException("身份证号已被其他用户占用");
        }
        Customer dbCustomer = customerService.findById(id).orElseThrow(() -> new RuntimeException("用户不存在"));
        // 只更新前端提交的字段，未提交的字段保留原值
        dbCustomer.setName(customer.getName() != null ? customer.getName() : dbCustomer.getName());
        dbCustomer.setPhone(customer.getPhone() != null ? customer.getPhone() : dbCustomer.getPhone());
        dbCustomer.setIdCard(customer.getIdCard() != null ? customer.getIdCard() : dbCustomer.getIdCard());
        if (customer.getPassword() != null && !customer.getPassword().isEmpty()) {
            dbCustomer.setPassword(customer.getPassword());
        }
        // password、username、email等未提交字段自动保留
        return customerService.save(dbCustomer);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteById(id);
    }

    @GetMapping(params = "username")
    public Customer getCustomerByUsername(@RequestParam String username) {
        return customerService.findByUsername(username);
    }

    @GetMapping(params = "idCard")
    public List<Customer> getCustomersByIdCard(@RequestParam String idCard) {
        Customer customer = customerService.findByIdCard(idCard);
        return customer != null ? List.of(customer) : List.of();
    }

    // @GetMapping("/{id}/records")
    // public List<com.example.hotelmanagement.entity.Room> getCustomerRecords(@PathVariable Long id) {
    //     return roomService.findByCustomerId(id);
    // }
} 