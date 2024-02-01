package com.invoices.app.services;

import java.util.List;

import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.invoices.app.models.dao.CustomerRepository;
import com.invoices.app.models.dto.CustomerDto;
import com.invoices.app.models.dto.CustomersWithoutInvoicesDto;
import com.invoices.app.models.entities.Customer;
import com.invoices.app.services.exceptions.NotFoundException;
import com.invoices.app.services.exceptions.SaveException;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

  private final CustomerRepository customerRepository;
  private final ConversionService conversionService;

  @Transactional(readOnly = true)
  public List<CustomersWithoutInvoicesDto> findAllCustomers() {
    return this.customerRepository.findAll()
        .stream()
        .map(customer -> conversionService.convert(customer, CustomersWithoutInvoicesDto.class))
        .toList();
  }

  @Transactional(readOnly = true)
  public List<CustomersWithoutInvoicesDto> findAllCustomersWithoutInvoices() {
    return this.customerRepository.findAllWithoutInvoices()
        .stream()
        .map(customer -> this.conversionService.convert(customer, CustomersWithoutInvoicesDto.class))
        .toList();
  }

  @Transactional(readOnly = true)
  public List<CustomerDto> findAllCustomersWithInvoices() {
    return this.customerRepository.findAll()
        .stream()
        .map(customer -> this.conversionService.convert(customer,
            CustomerDto.class))
        .toList();
  }

  @Transactional(readOnly = true)
  public CustomerDto findCustomerById(@NonNull Long id) {
    Customer customer = this.customerRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Customer with ID " + id + " not found"));

    return this.conversionService.convert(customer, CustomerDto.class);
  }

  @Transactional
  public CustomersWithoutInvoicesDto updateCustomer(@NonNull Long id,
      @NonNull CustomersWithoutInvoicesDto customersWithoutInvoices) {

    Customer existingCustomer = this.customerRepository.findById(id)
        .orElseThrow(
            () -> new EntityNotFoundException("Customer not found with ID: " + id));

    existingCustomer.setName(customersWithoutInvoices.getName());
    existingCustomer.setLastName(customersWithoutInvoices.getLastName());
    existingCustomer.setAddress(customersWithoutInvoices.getAddress());
    existingCustomer.setPhone(customersWithoutInvoices.getPhone());
    existingCustomer.setEmail(customersWithoutInvoices.getEmail());
    existingCustomer.setNit(customersWithoutInvoices.getNit());
    existingCustomer.setCity(customersWithoutInvoices.getCity());
    existingCustomer.setState(customersWithoutInvoices.getState());
    existingCustomer.setCountry(customersWithoutInvoices.getCountry());
    existingCustomer.setZip(customersWithoutInvoices.getZip());

    try {
      existingCustomer = this.customerRepository.save(existingCustomer);

      return this.conversionService.convert(existingCustomer, CustomersWithoutInvoicesDto.class);

    } catch (DataIntegrityViolationException e) {
      throw new SaveException("Error updating customer: Unable to update customer information", e);
    }
  }

  @Transactional
  public CustomersWithoutInvoicesDto newCustomer(@NonNull CustomersWithoutInvoicesDto customersWithoutInvoicesDto) {

    Customer customer = this.conversionService.convert(customersWithoutInvoicesDto, Customer.class);

    if (customer == null) {
      throw new SaveException("Error saving customer: Unable to save customer information", null);
    }

    try {
      customer = this.customerRepository.save(customer);

      return this.conversionService.convert(customer, CustomersWithoutInvoicesDto.class);

    } catch (DataIntegrityViolationException e) {

      throw new SaveException("Error saving customer: Unable to save customer information", e);
    }
  }

  public void deleteCustomer(@NonNull Long id) {

    this.customerRepository.deleteById(id);
  }

}
