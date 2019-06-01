package com.example.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecommerce.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
