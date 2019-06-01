package com.example.ecommerce.controllers;

import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.models.Product;
import com.example.ecommerce.services.ProductService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = { "", "/" })
    public @NotNull Iterable<Product> getProducts() {
        return productService.getAllProducts();
    }
    
    @PostMapping("/add")
    public Product addProduct(Product product) {
    	System.out.print(product);
    	return productService.save(product);
    }
    
    @GetMapping("/{id}")
    public Product getProduct(@Valid @PathVariable long id) {
    	return productService.getProduct(id);
    }
    
    @DeleteMapping("/delete")
    public void deleteProduct(long id) {
    	productService.deleteProductById(id);
    }
}
