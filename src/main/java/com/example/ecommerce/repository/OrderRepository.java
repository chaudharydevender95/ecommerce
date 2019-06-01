package com.example.ecommerce.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.ecommerce.models.Order;

public interface OrderRepository extends CrudRepository<Order, Long>{

}
