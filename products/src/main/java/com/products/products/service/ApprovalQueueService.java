package com.products.products.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.products.products.entity.ApprovalQueue;
import com.products.products.entity.Product;
import com.products.products.repository.ApprovalQueueRepository;
import com.products.products.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class ApprovalQueueService {

	@Autowired
	private ApprovalQueueRepository approvalQueueRepository;

	@Autowired
	private ProductRepository productRepository;

	public List<ApprovalQueue> getApprovalQueue() {
		return approvalQueueRepository.findAllByOrderByCreatedAtAsc();
	}

	public void addToApprovalQueue(Long productId, String action) {
		ApprovalQueue approvalQueue = new ApprovalQueue();
		approvalQueue.setProductId(productId);
		approvalQueue.setAction(action);
		if (approvalQueue.getCreatedAt() == null) {
			approvalQueue.setCreatedAt(LocalDateTime.now());
		}
		approvalQueueRepository.save(approvalQueue);
	}

	@Transactional
	public void approveProduct(Long approvalId) {
        ApprovalQueue approvalQueue = approvalQueueRepository.findById(approvalId)
                .orElseThrow(() -> new IllegalArgumentException("Approval queue entry not found"));
        Product product = productRepository.findById(approvalQueue.getProductId())
        		.orElseThrow(() -> new IllegalArgumentException("Product not found"));;
        product.setStatus(true);
        productRepository.save(product);
        approvalQueueRepository.deleteByProductId(product.getId());
    }

	public void rejectProduct(Long approvalId) {
		ApprovalQueue approvalQueue = approvalQueueRepository.findById(approvalId)
				.orElseThrow(() -> new IllegalArgumentException("Approval queue entry not found"));
		approvalQueueRepository.delete(approvalQueue);
	}
}
