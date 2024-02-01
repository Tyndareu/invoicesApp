package com.invoices.app.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.invoices.app.models.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
