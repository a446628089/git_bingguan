package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
 
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
} 