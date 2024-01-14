package com.invoices.app.models.services.customer;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.invoices.app.models.dao.ICustomerDao;
import com.invoices.app.models.entities.Customer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

  private final ICustomerDao customerDao;

  @Override
  @Transactional(readOnly = true)
  public List<Customer> findAllCustomers() {
    return customerDao.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public Customer findCustomerById(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("Customer ID can't be null");
    }
    return customerDao.findById(id)
        .orElseThrow(() -> new CustomerNotFoundException("Customer with ID " + id + " not found"));
  }

  @Override
  @Transactional
  public Customer saveCustomer(Customer customer) {

    if (customer == null) {
      throw new IllegalArgumentException("The customer cannot be null");
    }
    try {
      return customerDao.save(customer);
    } catch (DataIntegrityViolationException e) {
      if (e.getMessage().contains("Duplicate entry")) {
        throw new CustomerEmailAlreadyExistsException("Email already exists: " + customer.getEmail());
      } else {
        throw new CustomerSaveException("Error saving customer: Unable to save customer information", e);
      }
    }
  }

  @Override
  public void deleteCustomer(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("The customer id cannot be null");
    }
    customerDao.deleteById(id);
  }

  @Override
  public Page<Customer> findAll(Pageable pageable) {
    if (pageable == null) {
      throw new IllegalArgumentException("The pageable cannot be null");
    }
    return customerDao.findAll(pageable);
  }

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

}
