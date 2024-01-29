package com.invoices.app.services;

import java.util.List;

import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.invoices.app.models.dao.ICustomerDao;
import com.invoices.app.models.dao.IInvoiceDao;
import com.invoices.app.models.dto.InvoiceDto;
import com.invoices.app.models.entities.Customer;
import com.invoices.app.models.entities.Invoice;
import com.invoices.app.services.exceptions.NotFoundException;
import com.invoices.app.services.exceptions.SaveException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvoiceService {

  private static final String notFound = " not found";
  private static final String invoiceId = "Invoice with ID ";
  private static final String saveError = "Error saving customer: Unable to save customer information";

  private final IInvoiceDao invoiceDao;
  private final ICustomerDao customerDao;
  private final ConversionService conversionService;

  @Transactional(readOnly = true)
  public List<InvoiceDto> findAllInvoices() {
    return this.invoiceDao.findAll()
        .stream()
        .map(InvoiceDto::new)
        .toList();
  }

  @Transactional(readOnly = true)
  public InvoiceDto findInvoiceById(@NonNull Long id) {
    Invoice invoice = this.invoiceDao.findById(id)
        .orElseThrow(() -> new NotFoundException(invoiceId + id + notFound));
    return this.conversionService.convert(invoice, InvoiceDto.class);
  }

  @Transactional
  public InvoiceDto updateInvoice(@NonNull Long id, @NonNull InvoiceDto invoiceDto) {
    Invoice existingInvoice = this.invoiceDao.findById(id)
        .orElseThrow(() -> new NotFoundException(invoiceId + id + notFound));

    existingInvoice.setDescription(invoiceDto.getDescription());
    existingInvoice.setObservation(invoiceDto.getObservation());
    existingInvoice.setAmount(invoiceDto.getAmount());
    existingInvoice.setStatus(invoiceDto.getStatus());

    try {
      existingInvoice = this.invoiceDao.save(existingInvoice);
      return this.conversionService.convert(existingInvoice, InvoiceDto.class);

    } catch (Exception e) {
      throw new SaveException(saveError, e);
    }
  }

  @Transactional
  public InvoiceDto newInvoice(@NonNull Long customerId, @NonNull InvoiceDto newInvoiceDto) {

    Customer customer = this.customerDao.findById(customerId)
        .orElseThrow(() -> new RuntimeException("Customer Id" + customerId + notFound));

    Invoice invoice = this.conversionService.convert(newInvoiceDto, Invoice.class);

    if (invoice == null || customer == null) {
      throw new SaveException(saveError);
    }
    invoice.setCustomer(customer);

    try {
      invoice = this.invoiceDao.save(invoice);
      return this.conversionService.convert(invoice, InvoiceDto.class);

    } catch (Exception e) {
      throw new SaveException(saveError, e);
    }
  }

  @Transactional
  public void deleteInvoice(@NonNull Long id) {
    this.invoiceDao.deleteById(id);
  }

  public Page<Invoice> findAll(Pageable pageable) {
    throw new UnsupportedOperationException("Unimplemented method 'findAll'");
  }
}