package com.invoices.app.models.services;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.invoices.app.models.dao.ICustomerDao;
import com.invoices.app.models.dto.CustomerDto;
import com.invoices.app.models.dto.CustomerNotInvoicesDto;
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
  public List<CustomerNotInvoicesDto> findAllCustomers() {
    return customerDao.findAll()
        .stream()
        .map(
            CustomerNotInvoicesDto::new)
        .toList();
  }

  @Transactional(readOnly = true)
  public List<CustomerNotInvoicesDto> findAllCustomersNotInvoices() {
    return customerDao.findAllCustomersNotInvoices();
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
  public CustomerNotInvoicesDto updateCustomer(CustomerNotInvoicesDto customerNotInvoicesDto) {
    if (customerNotInvoicesDto == null || customerNotInvoicesDto.getId() == null) {
      throw new IllegalArgumentException("The customer ID cannot be null");
    }

    Customer existingCustomer = customerDao.findById(customerNotInvoicesDto.getId())
        .orElseThrow(
            () -> new EntityNotFoundException("Customer not found with ID: " + customerNotInvoicesDto.getId()));

    existingCustomer.setName(customerNotInvoicesDto.getName());
    existingCustomer.setLastName(customerNotInvoicesDto.getLastName());
    existingCustomer.setAddress(customerNotInvoicesDto.getAddress());
    existingCustomer.setPhone(customerNotInvoicesDto.getPhone());
    existingCustomer.setEmail(customerNotInvoicesDto.getEmail());
    existingCustomer.setNit(customerNotInvoicesDto.getNit());
    existingCustomer.setCity(customerNotInvoicesDto.getCity());
    existingCustomer.setState(customerNotInvoicesDto.getState());
    existingCustomer.setCountry(customerNotInvoicesDto.getCountry());
    existingCustomer.setZip(customerNotInvoicesDto.getZip());

    try {
      existingCustomer = customerDao.save(existingCustomer);

      return new CustomerNotInvoicesDto(existingCustomer);

    } catch (DataIntegrityViolationException e) {
      throw new CustomerSaveException("Error updating customer: Unable to update customer information", e);
    }
  }

  @Transactional
  public CustomerNotInvoicesDto newCustomer(CustomerNotInvoicesDto customerNotInvoicesDto) {
    if (customerNotInvoicesDto == null) {
      throw new IllegalArgumentException("The customer cannot be null");
    }
    Customer customer = convertToEntityNotInvoices(customerNotInvoicesDto);
    try {
      customer = customerDao.save(customer);

      return new CustomerNotInvoicesDto(customer);

    } catch (DataIntegrityViolationException e) {

      throw new CustomerSaveException("Error saving customer: Unable to save customer information", e);
    }
  }

  public void deleteCustomer(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("The customer id cannot be null");
    }
    customerDao.deleteById(id);
  }

  public Page<Customer> findAll(Pageable pageable) {
    if (pageable == null) {
      throw new IllegalArgumentException("The pageable cannot be null");
    }
    return customerDao.findAll(pageable);
  }

  private Customer convertToEntityNotInvoices(CustomerNotInvoicesDto customerNotInvoicesDto) {
    Customer customer = new Customer();
    customer.setId(customerNotInvoicesDto.getId());
    customer.setName(customerNotInvoicesDto.getName());
    customer.setLastName(customerNotInvoicesDto.getLastName());
    customer.setEmail(customerNotInvoicesDto.getEmail());
    customer.setAddress(customerNotInvoicesDto.getAddress());
    customer.setPhone(customerNotInvoicesDto.getPhone());
    customer.setNit(customerNotInvoicesDto.getNit());
    customer.setCity(customerNotInvoicesDto.getCity());
    customer.setState(customerNotInvoicesDto.getState());
    customer.setCountry(customerNotInvoicesDto.getCountry());
    customer.setZip(customerNotInvoicesDto.getZip());

    return customer;
  }

}
