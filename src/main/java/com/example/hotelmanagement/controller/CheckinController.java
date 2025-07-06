package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.entity.Customer;
import com.example.hotelmanagement.entity.Room;
import com.example.hotelmanagement.entity.CheckinRecord;
import com.example.hotelmanagement.service.CustomerService;
import com.example.hotelmanagement.service.RoomService;
import com.example.hotelmanagement.service.CheckinRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CheckinController {
    @Autowired
    private RoomService roomService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CheckinRecordService checkinRecordService;

    // 办理入住
    @PostMapping("/checkin")
    public Map<String, Object> checkin(@RequestBody Map<String, Object> req) {
        String customerName = (String) req.get("customerName");
        String customerPhone = (String) req.get("customerPhone");
        String idCard = (String) req.get("idCard");
        Long roomId = Long.valueOf(req.get("roomId").toString());
        Room room = roomService.findById(roomId).orElse(null);
        if (room == null || !"空闲".equals(room.getStatus())) {
            throw new RuntimeException("房间不可用");
        }
        // 新建或复用客户
        Customer customer = customerService.findByIdCard(idCard);
        if (customer == null) {
            customer = new Customer();
            customer.setName(customerName);
            customer.setPhone(customerPhone);
            customer.setIdCard(idCard);
            customer.setPassword("123456"); // 默认密码
            customer = customerService.save(customer);
        } else {
            // 校验姓名和手机号
            if (!customerName.equals(customer.getName()) || !customerPhone.equals(customer.getPhone())) {
                throw new RuntimeException("客户姓名或手机号不匹配");
            }
        }
        // 更新房间状态
        room.setStatus("已入住");
        room.setCustomer(customer);
        room.setCheckinTime(LocalDateTime.now());
        roomService.save(room);
        // 新增入住历史
        CheckinRecord record = new CheckinRecord();
        record.setCustomer(customer);
        record.setRoom(room);
        record.setCheckinTime(room.getCheckinTime());
        record.setPrice(room.getPrice());
        checkinRecordService.save(record);
        Map<String, Object> res = new HashMap<>();
        res.put("msg", "办理入住成功");
        return res;
    }

    // 查询客户当前入住信息
    @GetMapping("/checkout/query")
    public Map<String, Object> queryCheckout(@RequestParam String idCard, @RequestParam String name) {
        Customer customer = customerService.findByIdCard(idCard);
        Map<String, Object> res = new HashMap<>();
        if (customer != null && name.equals(customer.getName())) {
            Room room = roomService.findByCustomerIdAndStatus(customer.getId(), "已入住");
            if (room != null) {
                res.put("roomNumber", room.getRoomNumber());
                int days = 1;
                if (room.getCheckinTime() != null) {
                    days = (int) java.time.Duration.between(room.getCheckinTime(), java.time.LocalDateTime.now()).toDays();
                    if (java.time.Duration.between(room.getCheckinTime(), java.time.LocalDateTime.now()).toHours() % 24 != 0) days++;
                    if (days < 1) days = 1;
                }
                res.put("days", days);
                res.put("total", room.getPrice() * days);
            }
        }
        return res;
    }

    // 办理退房
    @PostMapping("/checkout")
    public Map<String, Object> checkout(@RequestBody Map<String, Object> req) {
        String idCard = (String) req.get("idCard");
        String name = (String) req.get("name");
        Customer customer = customerService.findByIdCard(idCard);
        if (customer == null || !name.equals(customer.getName())) {
            throw new RuntimeException("客户信息不匹配");
        }
        Room room = roomService.findByCustomerIdAndStatus(customer.getId(), "已入住");
        if (room == null) {
            throw new RuntimeException("未找到客户的入住房间");
        }
        int days = 1;
        if (room.getCheckinTime() != null) {
            days = (int) java.time.Duration.between(room.getCheckinTime(), java.time.LocalDateTime.now()).toDays();
            if (java.time.Duration.between(room.getCheckinTime(), java.time.LocalDateTime.now()).toHours() % 24 != 0) days++;
            if (days < 1) days = 1;
        }
        double total = room.getPrice() * days;
        // 更新入住历史表，补全checkoutTime
        var records = checkinRecordService.findUncheckoutByCustomerId(customer.getId());
        for (CheckinRecord record : records) {
            if (record.getRoom().getId().equals(room.getId()) && record.getCheckoutTime() == null) {
                record.setCheckoutTime(LocalDateTime.now());
                checkinRecordService.save(record);
            }
        }
        // 更新房间状态
        room.setStatus("空闲");
        room.setCustomer(null);
        room.setCheckinTime(null);
        roomService.save(room);
        Map<String, Object> res = new HashMap<>();
        res.put("msg", "退房结算成功");
        res.put("total", total);
        return res;
    }
} 