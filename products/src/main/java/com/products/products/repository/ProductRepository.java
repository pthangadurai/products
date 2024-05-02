package com.products.products.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.products.products.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStatusOrderByCreatedAtDesc(Boolean status);

    List<Product> findByName(String productName);
    
    List<Product> findByPriceGreaterThan(BigDecimal minPrice);
    
    List<Product> findByPriceLessThan(BigDecimal maxPrice);
    
    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    
    List<Product> findByCreatedAtGreaterThan(LocalDateTime minPostedDate);
    
    List<Product> findByCreatedAtLessThan(LocalDateTime maxPostedDate);
    
    List<Product> findByCreatedAtBetween(LocalDateTime minPostedDate, LocalDateTime maxPostedDate);
    
    
}

