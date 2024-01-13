package com.invoices.app.models.services.invoice;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.invoices.app.models.entities.Invoice;

public interface IInvoiceService {
  
  public List<Invoice> findAllInvoices();

  public Invoice findInvoiceById(Long id);

  public Invoice saveInvoice(Invoice invoice);

  public void deleteInvoice(Long id);

  public Page<Invoice> findAll(Pageable pageable);

}
