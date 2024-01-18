package com.invoices.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.invoices.app.models.dto.CustomerNotInvoicesDto;
import com.invoices.app.models.entities.Customer;

public interface ICustomerDao extends JpaRepository<Customer, Long> {
  List<Customer> findByLastName(String lastName); // naming convention

  // @Query("SELECT c FROM Customer c WHERE c.email = :email") // no naming
  // convention
  // Customer findByEmail(@Param("email") String email);

  @Query("SELECT new com.invoices.app.models.dto.CustomerNotInvoicesDto(c) FROM Customer c")
  List<CustomerNotInvoicesDto> findAllCustomersNotInvoices();

}
