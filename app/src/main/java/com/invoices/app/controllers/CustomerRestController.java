package com.invoices.app.controllers;

import java.util.List;

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
import com.invoices.app.models.dto.CustomerNotInvoicesDto;
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

  public ResponseEntity<List<CustomerNotInvoicesDto>> getAllCustomers() {
    List<CustomerNotInvoicesDto> customerWithoutInvoicesDto = customerService.findAllCustomersNotInvoices();
    return !customerWithoutInvoicesDto.isEmpty() ? ResponseEntity.ok(customerWithoutInvoicesDto)
        : ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<CustomerDto> getCustomerById(@Valid @PathVariable Long id) {

    CustomerDto customerDto = customerService.findCustomerById(id);

    return ResponseEntity.ok(customerDto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<CustomerNotInvoicesDto> updateCustomer(@Valid @PathVariable Long id,
      @Valid @RequestBody CustomerNotInvoicesDto customerNotInvoicesDto) {
    if (id == null) {
      return ResponseEntity.badRequest().build();
    }
    customerService.updateCustomer(customerNotInvoicesDto);

    return ResponseEntity.ok(customerNotInvoicesDto);
  }

  @PostMapping()
  public ResponseEntity<CustomerNotInvoicesDto> newCustomer(@Valid @RequestBody CustomerNotInvoicesDto newCustomer) {
    customerService.newCustomer(newCustomer);
    return ResponseEntity.ok(newCustomer);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCustomer(@Valid @PathVariable Long id) {
    customerService.deleteCustomer(id);
    return ResponseEntity.noContent().build();
  }

}