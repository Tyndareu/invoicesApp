package com.invoices.app.controllers;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
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
import com.invoices.app.models.dto.CustomersWithoutInvoices;
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
  public ResponseEntity<List<CustomersWithoutInvoices>> getAllCustomersWithoutInvoices() {
    List<CustomersWithoutInvoices> customersWithoutInvoicesDto = customerService.findAllCustomersWithoutInvoices();
    return customersWithoutInvoicesDto.isEmpty() ? ResponseEntity.noContent().build()
        : ResponseEntity
            .ok(customersWithoutInvoicesDto);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CustomerDto> getCustomerById(@NonNull @PathVariable Long id) {
    CustomerDto customerDto = customerService.findCustomerById(id);
    return ResponseEntity.ok(customerDto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<CustomersWithoutInvoices> updateCustomer(@NonNull @PathVariable Long id,
      @NonNull @Valid @RequestBody CustomersWithoutInvoices customerNotInvoicesDto) {

    CustomersWithoutInvoices updatedCustomer = customerService.updateCustomer(id, customerNotInvoicesDto);
    return ResponseEntity.ok(updatedCustomer);
  }

  @PostMapping()
  public ResponseEntity<CustomersWithoutInvoices> newCustomer(
      @Valid @NonNull @RequestBody CustomersWithoutInvoices newCustomer) {
    customerService.newCustomer(newCustomer);
    return ResponseEntity.ok(newCustomer);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCustomer(@NonNull @PathVariable Long id) {
    customerService.deleteCustomer(id);
    return ResponseEntity.noContent().build();
  }
}
