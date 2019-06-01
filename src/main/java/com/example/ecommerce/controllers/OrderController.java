package com.example.ecommerce.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.ecommerce.dto.OrderProductDto;
import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.models.Order;
import com.example.ecommerce.models.OrderProduct;
import com.example.ecommerce.models.OrderStatus;
import com.example.ecommerce.models.Product;
import com.example.ecommerce.services.OrderProductService;
import com.example.ecommerce.services.OrderService;
import com.example.ecommerce.services.ProductService;

import javax.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	ProductService productService;
    OrderService orderService;
    OrderProductService orderProductService;

    public OrderController(ProductService productService, OrderService orderService, OrderProductService orderProductService) {
        this.productService = productService;
        this.orderService = orderService;
        this.orderProductService = orderProductService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public @NotNull Iterable<Order> list() {
        return this.orderService.getAllOrders();
    }

    @PostMapping
    public ResponseEntity<Order> create() {
    	// Dummy DTO formation Starts
    	OrderProductDto orderProductDto1 = new OrderProductDto();
    	orderProductDto1.setProduct(productService.getProduct(1));
    	orderProductDto1.setQuantity(3);
    	
    	OrderProductDto orderProductDto2 = new OrderProductDto();
    	orderProductDto2.setProduct(productService.getProduct(2));
    	orderProductDto2.setQuantity(2);
    	
    	List<OrderProductDto> orderProductDtos = new ArrayList<OrderProductDto>();
    	orderProductDtos.add(orderProductDto1);
    	orderProductDtos.add(orderProductDto2);
    	
    	OrderForm orderForm = new OrderForm();
    	orderForm.setProductOrders(orderProductDtos);
    	// Dummy DTO formation Ends
    	 	
        List<OrderProductDto> formDtos = orderForm.getProductOrders();
        validateProductsExistence(formDtos);
        Order order = new Order();
        order.setStatus(OrderStatus.PAID.name());
        order.setEmail("Email Address");
        order = this.orderService.create(order);

        List<OrderProduct> orderProducts = new ArrayList<>();
        for (OrderProductDto dto : formDtos) {
            orderProducts.add(orderProductService.create(new OrderProduct(order, productService.getProduct(dto
              .getProduct()
              .getId()), dto.getQuantity())));
        }

        return new ResponseEntity<>(order,  HttpStatus.CREATED);
    }

    private void validateProductsExistence(List<OrderProductDto> orderProducts) {
    	List<OrderProductDto> list = null;
    	try {
        list = orderProducts
          .stream()
          .filter(op -> Objects.isNull(productService.getProduct(op
        		  .getProduct()
            .getId())))
          .collect(Collectors.toList());
    	}catch(Exception e) {
    		System.out.println(e.getMessage());
    	}
        if (!CollectionUtils.isEmpty(list)) {
            new ResourceNotFoundException("Product not found");
        }
    }

    public static class OrderForm {

        private List<OrderProductDto> productOrders;

        public List<OrderProductDto> getProductOrders() {
            return productOrders;
        }

        public void setProductOrders(List<OrderProductDto> productOrders) {
            this.productOrders = productOrders;
        }
    }
}
