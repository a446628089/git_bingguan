package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByCustomerId(Long customerId);
    Room findByCustomerIdAndStatus(Long customerId, String status);
    List<Room> findByRoomNumber(String roomNumber);
} 