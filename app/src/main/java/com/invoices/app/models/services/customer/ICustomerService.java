package com.invoices.app.models.services.customer;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.invoices.app.models.entities.Customer;

public interface ICustomerService {

  public List<Customer> findAllCustomers();

  public Customer findCustomerById(Long id);

  public Customer saveCustomer(Customer customer);

  public void deleteCustomer(Long id);

  public Page<Customer> findAll(Pageable pageable);

}
