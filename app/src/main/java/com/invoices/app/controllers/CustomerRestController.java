package com.invoices.app.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.invoices.app.models.entities.Customer;
import com.invoices.app.models.services.ICustomerService;


@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {

  private final ICustomerService clientService;

  public CustomerRestController(ICustomerService clientService) {
    this.clientService = clientService;
  }

  @GetMapping()
  public List<Customer> getAllCustomers() {
      return clientService.findAllCustomers();
  }
  

}
