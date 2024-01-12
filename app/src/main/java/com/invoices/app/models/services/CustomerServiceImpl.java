package com.invoices.app.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.invoices.app.models.dao.ICustomerDao;
import com.invoices.app.models.entities.Customer;

@Service
public class CustomerServiceImpl implements ICustomerService {

  private final ICustomerDao customerDao;

  public CustomerServiceImpl(ICustomerDao customerDao) {
    this.customerDao = customerDao;
  }

  @Override
  @Transactional(readOnly = true)
  public List<Customer> findAllCustomers() {
    return customerDao.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public Customer findCustomerById(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("The customer id cannot be null");
    }
    return customerDao.findById(id)
        .orElseThrow(() -> new RuntimeException("Customer not found"));
  }

  @Override
  public Customer saveCustomer(Customer customer) {
    if (customer == null) {
      throw new IllegalArgumentException("The customer cannot be null");
    }
    return customerDao.save(customer);
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


}
