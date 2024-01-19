package com.invoices.app.models.services;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.invoices.app.models.dao.ICustomerDao;
import com.invoices.app.models.dto.CustomerDto;
import com.invoices.app.models.dto.CustomersWithoutInvoices;
import com.invoices.app.models.entities.Customer;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

  // * error control

  @ResponseStatus(HttpStatus.NOT_FOUND)
  public static class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String message) {
      super(message);
    }
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public class CustomerEmailAlreadyExistsException extends RuntimeException {
    public CustomerEmailAlreadyExistsException(String message) {
      super(message);
    }
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public class CustomerSaveException extends RuntimeException {
    public CustomerSaveException(String message, Throwable cause) {
      super(message, cause);
    }
  }

  // * End error control

  private final ICustomerDao customerDao;

  @Transactional(readOnly = true)
  public List<CustomersWithoutInvoices> findAllCustomers() {
    return customerDao.findAll()
        .stream()
        .map(
            CustomersWithoutInvoices::new)
        .toList();
  }

  @Transactional(readOnly = true)
  public List<CustomersWithoutInvoices> findAllCustomersWithoutInvoices() {
    return customerDao.findAllCustomersWithoutInvoices();
  }

  @Transactional(readOnly = true)
  public CustomerDto findCustomerById(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("Customer ID can't be null");
    }
    Customer customer = customerDao.findById(id)
        .orElseThrow(() -> new CustomerNotFoundException("Customer with ID " + id + " not found"));
    return new CustomerDto(customer);
  }

  @Transactional
  public CustomersWithoutInvoices updateCustomer(@NonNull Long id, CustomersWithoutInvoices customersWithoutInvoices) {
    if (customersWithoutInvoices == null) {
      throw new IllegalArgumentException("The customer ID cannot be null");
    }

    Customer existingCustomer = customerDao.findById(id)
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
      existingCustomer = customerDao.save(existingCustomer);

      return new CustomersWithoutInvoices(existingCustomer);

    } catch (DataIntegrityViolationException e) {
      throw new CustomerSaveException("Error updating customer: Unable to update customer information", e);
    }
  }

  @Transactional
  public CustomersWithoutInvoices newCustomer(@NonNull CustomersWithoutInvoices customersWithoutInvoices) {

    Customer customer = convertToEntityNotInvoices(customersWithoutInvoices);

    if (customer.getId() == null) {
      throw new IllegalArgumentException("The customer ID cannot be null");
    }

    try {
      customer = customerDao.save(customer);

      return new CustomersWithoutInvoices(customer);

    } catch (DataIntegrityViolationException e) {

      throw new CustomerSaveException("Error saving customer: Unable to save customer information", e);
    }
  }

  public void deleteCustomer(@NonNull Long id) {

    customerDao.deleteById(id);
  }

  public Page<Customer> findAll(Pageable pageable) {
    if (pageable == null) {
      throw new IllegalArgumentException("The pageable cannot be null");
    }
    return customerDao.findAll(pageable);
  }

  private Customer convertToEntityNotInvoices(CustomersWithoutInvoices customersWithoutInvoices) {
    Customer customer = new Customer();
    customer.setId(customersWithoutInvoices.getId());
    customer.setName(customersWithoutInvoices.getName());
    customer.setLastName(customersWithoutInvoices.getLastName());
    customer.setEmail(customersWithoutInvoices.getEmail());
    customer.setAddress(customersWithoutInvoices.getAddress());
    customer.setPhone(customersWithoutInvoices.getPhone());
    customer.setNit(customersWithoutInvoices.getNit());
    customer.setCity(customersWithoutInvoices.getCity());
    customer.setState(customersWithoutInvoices.getState());
    customer.setCountry(customersWithoutInvoices.getCountry());
    customer.setZip(customersWithoutInvoices.getZip());

    return customer;
  }

}
