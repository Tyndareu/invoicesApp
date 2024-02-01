package com.invoices.app.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.invoices.app.models.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

  // Query without Invoices
  @Query("SELECT c FROM Customer c")
  List<Customer> findAllWithoutInvoices();

}