package com.invoices.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.invoices.app.models.dto.CustomersWithoutInvoices;
import com.invoices.app.models.entities.Customer;

public interface ICustomerDao extends JpaRepository<Customer, Long> {
  List<Customer> findByLastName(String lastName); // naming convention

  @Query("SELECT new com.invoices.app.models.dto.CustomersWithoutInvoices(c) FROM Customer c")
  List<CustomersWithoutInvoices> findAllCustomersWithoutInvoices();

}
