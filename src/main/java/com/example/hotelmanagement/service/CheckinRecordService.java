package com.example.hotelmanagement.service;

import com.example.hotelmanagement.entity.CheckinRecord;
import com.example.hotelmanagement.repository.CheckinRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CheckinRecordService {
    @Autowired
    private CheckinRecordRepository checkinRecordRepository;

    public CheckinRecord save(CheckinRecord record) {
        return checkinRecordRepository.save(record);
    }

    public List<CheckinRecord> findByCustomerId(Long customerId) {
        return checkinRecordRepository.findByCustomerIdOrderByCheckinTimeDesc(customerId);
    }

    public List<CheckinRecord> findUncheckoutByRoomId(Long roomId) {
        return checkinRecordRepository.findByRoomIdAndCheckoutTimeIsNull(roomId);
    }

    public List<CheckinRecord> findUncheckoutByCustomerId(Long customerId) {
        return checkinRecordRepository.findByCustomerIdAndCheckoutTimeIsNull(customerId);
    }
} 