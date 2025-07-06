package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.entity.Room;
import com.example.hotelmanagement.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping
    public List<Room> getAllRooms() {
        return roomService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Room> getRoomById(@PathVariable Long id) {
        return roomService.findById(id);
    }

    @GetMapping(params = "roomNumber")
    public List<Room> getRoomsByRoomNumber(@RequestParam String roomNumber) {
        return roomService.findByRoomNumber(roomNumber);
    }

    @PostMapping
    public Room createRoom(@RequestBody Room room) {
        return roomService.save(room);
    }

    @PutMapping("/{id}")
    public Room updateRoom(@PathVariable Long id, @RequestBody Room room) {
        Room dbRoom = roomService.findById(id).orElseThrow(() -> new RuntimeException("房间不存在"));
        dbRoom.setRoomNumber(room.getRoomNumber() != null ? room.getRoomNumber() : dbRoom.getRoomNumber());
        dbRoom.setType(room.getType() != null ? room.getType() : dbRoom.getType());
        dbRoom.setPrice(room.getPrice() != null ? room.getPrice() : dbRoom.getPrice());
        dbRoom.setDescription(room.getDescription() != null ? room.getDescription() : dbRoom.getDescription());
        dbRoom.setStatus(room.getStatus() != null ? room.getStatus() : dbRoom.getStatus());
        return roomService.save(dbRoom);
    }

    @DeleteMapping("/{id}")
    public void deleteRoom(@PathVariable Long id) {
        roomService.deleteById(id);
    }
} 