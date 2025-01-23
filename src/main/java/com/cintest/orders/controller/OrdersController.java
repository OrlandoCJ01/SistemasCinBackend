package com.cintest.orders.controller;

import com.cintest.orders.dto.order.OrderRequest;
import com.cintest.orders.model.Order;
import com.cintest.orders.service.order.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/products")
    public Product addProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }
}

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        Product product = productRepository.findById(order.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        order.setProduct(product);
        return orderRepository.save(order);
    }

    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable Long id, @RequestBody Order updatedOrder) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Validar restricciones
        if (updatedOrder.getUnits() > existingOrder.getUnits() ||
            updatedOrder.getBonus() > existingOrder.getBonus() ||
            updatedOrder.getPromo() > existingOrder.getPromo()) {
            throw new RuntimeException("No puedes agregar más unidades, bonos o promociones");
        }

        existingOrder.setUnits(updatedOrder.getUnits());
        existingOrder.setBonus(updatedOrder.getBonus());
        existingOrder.setPromo(updatedOrder.getPromo());

        return orderRepository.save(existingOrder);
    }
}
