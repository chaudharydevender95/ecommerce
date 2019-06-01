package com.example.ecommerce.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.ecommerce.models.OrderProduct;
import com.example.ecommerce.models.OrderProductPK;

public interface OrderProductRepository  extends CrudRepository<OrderProduct, OrderProductPK> {

}
