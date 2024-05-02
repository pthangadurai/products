package com.products.products.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.products.products.entity.Product;
import com.products.products.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ApprovalQueueService approvalQueueService;

    public List<Product> listActiveProducts() {
        return productRepository.findByStatusOrderByCreatedAtDesc(true);
    }
    
    public static LocalDateTime convert(String dateString) {
        // Define the format pattern according to your string representation
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // Parse the string to LocalDateTime
        return LocalDateTime.parse(dateString, formatter);
    }

    public List<Product> searchProducts(String productName, BigDecimal minPrice, BigDecimal maxPrice, String minPostedDate, String maxPostedDate) {
        List<Product> products = null;
    	
    	if(productName != null) {
    		products = productRepository.findByName(productName);
    	}
    	
    	if(minPrice != null) {
    		products = productRepository.findByPriceGreaterThan(minPrice);
    	}
    	
    	if(maxPrice != null) {
    		products = productRepository.findByPriceLessThan(maxPrice);
    	}
    	
    	if(minPrice != null && maxPrice != null) {
    		products = productRepository.findByPriceBetween(minPrice, maxPrice);
    	}
    	
    	if(minPostedDate != null) {
    		LocalDateTime minPostedDateConverted = convert(minPostedDate);
    		products = productRepository.findByCreatedAtGreaterThan(minPostedDateConverted);
    	}
    	
    	if(maxPostedDate != null) {
    		LocalDateTime maxPostedDateConverted = convert(maxPostedDate);
    		products = productRepository.findByCreatedAtLessThan(maxPostedDateConverted);
    	}
    	
    	if(minPostedDate != null && maxPostedDate != null) {
    		LocalDateTime minPostedDateConverted = convert(minPostedDate);
    		LocalDateTime maxPostedDateConverted = convert(maxPostedDate);
    		products = productRepository.findByCreatedAtBetween(minPostedDateConverted, maxPostedDateConverted);
    	}
    	
    	return products;
    }

    public void createProduct(Product product) {
        if (product.getPrice().compareTo(BigDecimal.valueOf(10000)) > 0) {
            throw new IllegalArgumentException("Price cannot exceed $10,000");
        }
        if (product.getPrice().compareTo(BigDecimal.valueOf(5000)) > 0) {
        	product.setStatus(false);
            if(product.getCreatedAt() == null) {
            	product.setCreatedAt(LocalDateTime.now());
            }
            if(product.getUpdatedAt() == null) {
            	product.setUpdatedAt(LocalDateTime.now());
            }
            Product savedProduct = productRepository.save(product);
            approvalQueueService.addToApprovalQueue(savedProduct.getId(), "CREATE");
        } else {
            product.setStatus(true);
            if(product.getCreatedAt() == null) {
            	product.setCreatedAt(LocalDateTime.now());
            }
            if(product.getUpdatedAt() == null) {
            	product.setUpdatedAt(LocalDateTime.now());
            }
            productRepository.save(product);
        }
    }

    public void updateProduct(Long productId, Product product) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        BigDecimal previousPrice = existingProduct.getPrice();
        BigDecimal newPrice = product.getPrice();
        if (newPrice.compareTo(previousPrice.multiply(BigDecimal.valueOf(1.5))) > 0) {
            approvalQueueService.addToApprovalQueue(productId, "UPDATE");
        } else {
            existingProduct.setName(product.getName());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setStatus(product.getStatus());            
            if(product.getUpdatedAt() == null) {
            	product.setUpdatedAt(LocalDateTime.now());
            }
            productRepository.save(existingProduct);
        }
    }

    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        product.setStatus(false);
        productRepository.save(product);
        approvalQueueService.addToApprovalQueue(productId, "DELETE");
    }
}

