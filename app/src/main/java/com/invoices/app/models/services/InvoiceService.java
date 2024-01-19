package com.invoices.app.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.invoices.app.models.dao.ICustomerDao;
import com.invoices.app.models.dao.IInvoiceDao;
import com.invoices.app.models.dto.InvoiceDto;
import com.invoices.app.models.entities.Customer;
import com.invoices.app.models.entities.Invoice;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvoiceService {
  // * Errors Control

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public class InvoiceSaveException extends RuntimeException {
    InvoiceSaveException(String message, Throwable cause) {
      super(message, cause);
    }
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  static class InvoiceNotFoundException extends RuntimeException {
    public InvoiceNotFoundException(String message) {
      super(message);
    }
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  class InvoiceEmailAlreadyExistsException extends RuntimeException {
    public InvoiceEmailAlreadyExistsException(String message) {
      super(message);
    }
  }

  // * End Errors Control

  private static final String notFound = " not found";
  private static final String invoiceId = "Invoice with ID ";

  private final IInvoiceDao invoiceDao;

  private final ICustomerDao customerDao;

  @Transactional(readOnly = true)
  public List<InvoiceDto> findAllInvoices() {
    return invoiceDao.findAll()
        .stream()
        .map(InvoiceDto::new)
        .toList();
  }

  @Transactional(readOnly = true)
  public InvoiceDto findInvoiceById(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("Invoice ID can't be null");
    }
    Invoice invoice = invoiceDao.findById(id)
        .orElseThrow(() -> new InvoiceNotFoundException(invoiceId + id + notFound));
    return new InvoiceDto(invoice);
  }

  @Transactional
  public InvoiceDto updateInvoice(@NonNull Long id, InvoiceDto invoiceDto) {
    if (invoiceDto == null) {
      throw new IllegalArgumentException("Invoice can't be null");
    }

    Invoice existingInvoice = invoiceDao.findById(id)
        .orElseThrow(() -> new InvoiceNotFoundException(invoiceId + id + notFound));

    existingInvoice.setDescription(invoiceDto.getDescription());
    existingInvoice.setObservation(invoiceDto.getObservation());
    existingInvoice.setAmount(invoiceDto.getAmount());
    existingInvoice.setStatus(invoiceDto.getStatus());

    try {
      existingInvoice = invoiceDao.save(existingInvoice);
      return new InvoiceDto(existingInvoice);

    } catch (Exception e) {
      throw new InvoiceSaveException("Error saving customer: Unable to save customer information", e);
    }
  }

  @Transactional
  public InvoiceDto newInvoice(@NonNull Long customerId, @NonNull InvoiceDto newInvoiceDto) {

    Customer customer = customerDao.findById(customerId)
        .orElseThrow(() -> new RuntimeException("Customer Id" + customerId + notFound));

    Invoice invoice = convertToEntityInvoice(newInvoiceDto);
    invoice.setCustomer(customer);

    try {
      invoice = invoiceDao.save(invoice);
      return new InvoiceDto(invoice);
    } catch (Exception e) {
      throw new InvoiceSaveException("Error saving customer: Unable to save customer information", e);
    }
  }

  @Transactional
  public void deleteInvoice(@NonNull Long id) {

    invoiceDao.deleteById(id);
  }

  public Page<Invoice> findAll(Pageable pageable) {
    throw new UnsupportedOperationException("Unimplemented method 'findAll'");
  }

  private Invoice convertToEntityInvoice(InvoiceDto invoiceDto) {
    Invoice invoice = new Invoice();
    invoice.setAmount(invoiceDto.getAmount());
    invoice.setStatus(invoiceDto.getStatus());
    invoice.setObservation(invoiceDto.getObservation());
    invoice.setDescription(invoiceDto.getDescription());

    return invoice;

  }
}