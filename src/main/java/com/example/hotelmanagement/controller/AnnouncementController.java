package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.entity.Announcement;
import com.example.hotelmanagement.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {
    @Autowired
    private AnnouncementService announcementService;

    @GetMapping
    public List<Announcement> getAll() {
        return announcementService.findAll();
    }

    @PostMapping
    public Announcement add(@RequestBody Map<String, String> req) {
        Announcement a = new Announcement();
        a.setContent(req.get("content"));
        a.setCreatedAt(LocalDateTime.now());
        return announcementService.save(a);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        announcementService.deleteById(id);
    }
} 