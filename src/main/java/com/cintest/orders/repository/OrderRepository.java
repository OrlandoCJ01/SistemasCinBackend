package com.cintest.orders.repository;

import com.cintest.orders.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}

