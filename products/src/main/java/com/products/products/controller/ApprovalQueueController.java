package com.products.products.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.products.products.entity.ApprovalQueue;
import com.products.products.service.ApprovalQueueService;

@RestController
@RequestMapping("/api/products/approval-queue")
public class ApprovalQueueController {

    @Autowired
    private ApprovalQueueService approvalQueueService;

    @GetMapping
    public ResponseEntity<List<ApprovalQueue>> getApprovalQueue() {
        List<ApprovalQueue> approvalQueue = approvalQueueService.getApprovalQueue();
        return ResponseEntity.ok().body(approvalQueue);
    }

    @PutMapping("/{approvalId}/approve")
    public ResponseEntity<?> approveProduct(@PathVariable Long approvalId) {
        approvalQueueService.approveProduct(approvalId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{approvalId}/reject")
    public ResponseEntity<?> rejectProduct(@PathVariable Long approvalId) {
        approvalQueueService.rejectProduct(approvalId);
        return ResponseEntity.ok().build();
    }
}
