package com.invoices.app.models.services.invoice;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.invoices.app.models.dao.ICustomerDao;
import com.invoices.app.models.dao.IInvoiceDao;
import com.invoices.app.models.entities.Customer;
import com.invoices.app.models.entities.Invoice;
import com.invoices.app.models.services.customer.CustomerServiceImpl.CustomerSaveException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements IInvoiceService {

  private final IInvoiceDao invoiceDao;
  private final ICustomerDao customerDao;

  @Override
  @Transactional(readOnly = true)
  public List<Invoice> findAllInvoices() {
    return invoiceDao.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public Invoice findInvoiceById(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("Invoice ID can't be null");
    }
    return invoiceDao.findById(id)
        .orElseThrow(() -> new InvoiceNotFoundException("Invoice with ID " + id + " not found"));
  }

  @Override
  @Transactional
  public Invoice updateInvoice(Invoice invoice) {
    if (invoice == null) {
      throw new IllegalArgumentException("Invoice can't be null");
    }
    try {
      return invoiceDao.save(invoice);
    } catch (Exception e) {
      throw new InvoiceSaveException("Error saving customer: Unable to save customer information", e);
    }
  }

  @Override
  @Transactional
  public Invoice newInvoice(Long customerId, Invoice newInvoice) {
    if (customerId == null) {
      throw new IllegalArgumentException("Customer ID can't be null");
    }
    Customer customer = customerDao.findById(customerId)
        .orElseThrow(() -> new RuntimeException("Customer Id" + customerId + " not found"));

    newInvoice.setCustomer(customer);

    return invoiceDao.save(newInvoice);
  }

  @Override
  public void deleteInvoice(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("Invoice ID can't be null");
    }
    invoiceDao.deleteById(id);
  }

  @Override
  public Page<Invoice> findAll(Pageable pageable) {
    throw new UnsupportedOperationException("Unimplemented method 'findAll'");
  }

  // * error control

  @ResponseStatus(HttpStatus.NOT_FOUND)
  public static class InvoiceNotFoundException extends RuntimeException {
    public InvoiceNotFoundException(String message) {
      super(message);
    }
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public class InvoiceEmailAlreadyExistsException extends RuntimeException {
    public InvoiceEmailAlreadyExistsException(String message) {
      super(message);
    }
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public class InvoiceSaveException extends RuntimeException {
    public InvoiceSaveException(String message, Throwable cause) {
      super(message, cause);
    }
  }

}
