package com.example.ecommerce.services;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.example.ecommerce.models.Product;

@Validated
public interface ProductService {

	@NotNull Iterable<Product> getAllProducts();

    Product getProduct(@Min(value = 1L, message = "Invalid product ID.") long id);

    Product save(Product product);
    void deleteProductById(@Min(value = 1L, message = "Invalid product ID.")long id);
   
}
