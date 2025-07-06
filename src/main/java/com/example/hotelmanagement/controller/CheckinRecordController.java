package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.entity.CheckinRecord;
import com.example.hotelmanagement.service.CheckinRecordService;
import com.example.hotelmanagement.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/checkin-records")
public class CheckinRecordController {
    @Autowired
    private CheckinRecordService checkinRecordService;
    @Autowired
    private CustomerService customerService;

    @GetMapping("/by-customer")
    public List<CheckinRecord> getByCustomer(@RequestParam String idCard) {
        var customer = customerService.findByIdCard(idCard);
        if (customer == null) return List.of();
        return checkinRecordService.findByCustomerId(customer.getId());
    }
} 