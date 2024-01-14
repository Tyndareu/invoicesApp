package com.invoices.app.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.invoices.app.models.entities.Customer;
import com.invoices.app.models.services.customer.ICustomerService;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/customers")
public class CustomerRestController {

  private final ICustomerService customerService;

  public CustomerRestController(ICustomerService customerService) {
    this.customerService = customerService;
  }

  @GetMapping
  public ResponseEntity<List<Customer>> getAllCustomers() {
    List<Customer> customers = customerService.findAllCustomers();

    if (customers.isEmpty()) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.ok(customers);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {

    Customer customer = customerService.findCustomerById(id);

    return ResponseEntity.ok(customer);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customerUpdate) {

    Customer customer = customerService.findCustomerById(id);

    customer.copyFrom(customerUpdate);

    customerService.saveCustomer(customer);

    return ResponseEntity.ok(customer);
  }

  @PostMapping()
  public ResponseEntity<Customer> saveCustomer(@RequestBody Customer newCustomer) {
    customerService.saveCustomer(newCustomer);
    return ResponseEntity.ok(newCustomer);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
    customerService.deleteCustomer(id);
    return ResponseEntity.noContent().build();
  }
}