package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.entity.Comment;
import com.example.hotelmanagement.entity.Customer;
import com.example.hotelmanagement.service.CommentService;
import com.example.hotelmanagement.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<Map<String, Object>> getAllComments() {
        return commentService.findAll().stream().map(c -> {
            Map<String, Object> m = new HashMap<>();
            m.put("id", c.getId());
            m.put("content", c.getContent());
            m.put("createdAt", c.getCreatedAt());
            m.put("anonymous", c.getAnonymous() != null && c.getAnonymous());
            if (c.getCustomer() != null) {
                m.put("customerId", c.getCustomer().getId());
                m.put("customerName", c.getCustomer().getName());
            } else {
                m.put("customerId", null);
                m.put("customerName", "");
            }
            return m;
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Optional<Comment> getCommentById(@PathVariable Long id) {
        return commentService.findById(id);
    }

    @PostMapping
    public Map<String, Object> createComment(@RequestBody Map<String, Object> req) {
        Comment comment = new Comment();
        comment.setContent((String) req.get("content"));
        Object customerIdObj = req.get("customerId");
        if (customerIdObj != null && !customerIdObj.toString().isEmpty()) {
            try {
                Long customerId = Long.valueOf(customerIdObj.toString());
                Customer customer = customerService.findById(customerId).orElse(null);
                if (customer != null) {
                    comment.setCustomer(customer);
                }
            } catch (Exception e) {
                // ignore parse error
            }
        }
        comment.setCreatedAt(LocalDateTime.now());
        Object anonymousObj = req.get("anonymous");
        comment.setAnonymous(anonymousObj != null && Boolean.parseBoolean(anonymousObj.toString()));
        Comment saved = commentService.save(comment);
        Map<String, Object> m = new HashMap<>();
        m.put("id", saved.getId());
        m.put("content", saved.getContent());
        m.put("createdAt", saved.getCreatedAt());
        m.put("anonymous", saved.getAnonymous() != null && saved.getAnonymous());
        if (saved.getCustomer() != null) {
            m.put("customerId", saved.getCustomer().getId());
            m.put("customerName", saved.getCustomer().getName());
        } else {
            m.put("customerId", null);
            m.put("customerName", "");
        }
        return m;
    }

    @PutMapping("/{id}")
    public Comment updateComment(@PathVariable Long id, @RequestBody Map<String, Object> req) {
        Comment comment = commentService.findById(id).orElse(null);
        if (comment == null) return null;
        comment.setContent((String) req.get("content"));
        Object anonymousObj = req.get("anonymous");
        comment.setAnonymous(anonymousObj != null && Boolean.parseBoolean(anonymousObj.toString()));
        return commentService.save(comment);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteById(id);
    }
} 