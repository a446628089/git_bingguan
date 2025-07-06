package com.example.hotelmanagement.service;

import com.example.hotelmanagement.entity.Room;
import com.example.hotelmanagement.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    public Optional<Room> findById(Long id) {
        return roomRepository.findById(id);
    }

    public Room save(Room room) {
        return roomRepository.save(room);
    }

    public void deleteById(Long id) {
        roomRepository.deleteById(id);
    }

    public List<com.example.hotelmanagement.entity.Room> findByCustomerId(Long customerId) {
        return roomRepository.findByCustomerId(customerId);
    }

    public Room findByCustomerIdAndStatus(Long customerId, String status) {
        return roomRepository.findByCustomerIdAndStatus(customerId, status);
    }

    public List<Room> findByRoomNumber(String roomNumber) {
        return roomRepository.findByRoomNumber(roomNumber);
    }
} 