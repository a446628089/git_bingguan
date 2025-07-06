package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.entity.CheckinRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
 
public interface CheckinRecordRepository extends JpaRepository<CheckinRecord, Long> {
    List<CheckinRecord> findByCustomerIdOrderByCheckinTimeDesc(Long customerId);
    List<CheckinRecord> findByRoomIdAndCheckoutTimeIsNull(Long roomId);
    List<CheckinRecord> findByCustomerIdAndCheckoutTimeIsNull(Long customerId);
} 