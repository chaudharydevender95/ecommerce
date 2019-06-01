package com.example.ecommerce.services;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.models.Product;
import com.example.ecommerce.repository.ProductRepository;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(long id) {
        return productRepository
          .findById(id)
          .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

	@Override
	public
	void deleteProductById(long id) {
		Product product = getProduct(id);
		productRepository.deleteById(id);
	}

    
}
