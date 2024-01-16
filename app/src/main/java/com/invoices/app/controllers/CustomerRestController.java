package com.invoices.app.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
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

import com.invoices.app.models.dto.CustomerDto;
import com.invoices.app.models.dto.CustomerInvoicesDto;
import com.invoices.app.models.entities.Customer;
import com.invoices.app.models.services.CustomerService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/customers")
public class CustomerRestController {

  private final CustomerService customerService;

  public CustomerRestController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @GetMapping
  @Cacheable("customers")

  public ResponseEntity<List<CustomerDto>> getAllCustomers() {
    List<CustomerDto> customersDto = customerService
        .findAllCustomers()
        .stream()
        .map(CustomerDto::new)
        .collect(Collectors.toList());

       return !customersDto.isEmpty() ? ResponseEntity.ok(customersDto) : ResponseEntity.noContent().build();

  }

  @GetMapping("/{id}")
  public ResponseEntity<CustomerInvoicesDto> getCustomerById(@PathVariable Long id) {

    CustomerInvoicesDto customerInvoicesDto = new CustomerInvoicesDto(customerService.findCustomerById(id));

    return ResponseEntity.ok(customerInvoicesDto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<CustomerDto> updateCustomer(@PathVariable Long id,
      @Valid @RequestBody Customer customerUpdate) {
    if (id == null) {
      return ResponseEntity.badRequest().build();
    }

    Customer customer = customerService.findCustomerById(id);
    customer.copyFrom(customerUpdate);
    customerService.saveCustomer(customer);
    CustomerDto customerDto = new CustomerDto(customer);

    return ResponseEntity.ok(customerDto);
  }

  @PostMapping()
  public ResponseEntity<CustomerDto> saveCustomer(@Valid @RequestBody Customer newCustomer) {
    customerService.saveCustomer(newCustomer);
    CustomerDto newCustomerDto = new CustomerDto(newCustomer);
    return ResponseEntity.ok(newCustomerDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
    customerService.deleteCustomer(id);
    return ResponseEntity.noContent().build();
  }
}