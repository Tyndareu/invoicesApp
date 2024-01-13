package com.invoices.app.models.services.invoice;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.invoices.app.models.dao.IInvoiceDao;
import com.invoices.app.models.entities.Invoice;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements IInvoiceService {

  private final IInvoiceDao invoiceDao;

  @Override
  @Transactional(readOnly = true)
  public List<Invoice> findAllInvoices() {
    return invoiceDao.findAll(); 
  }

  @Override
  public Invoice findInvoiceById(Long id) {
    throw new UnsupportedOperationException("Unimplemented method 'findInvoiceById'");
  }

  @Override
  public Invoice saveInvoice(Invoice invoice) {
    throw new UnsupportedOperationException("Unimplemented method 'saveInvoice'");
  }

  @Override
  public void deleteInvoice(Long id) {
    throw new UnsupportedOperationException("Unimplemented method 'deleteInvoice'");
  }

  @Override
  public Page<Invoice> findAll(Pageable pageable) {
    throw new UnsupportedOperationException("Unimplemented method 'findAll'");
  }

}
