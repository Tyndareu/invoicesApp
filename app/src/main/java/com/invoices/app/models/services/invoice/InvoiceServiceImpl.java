package com.invoices.app.models.services.invoice;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;

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
    if (id == null) {
      throw new IllegalArgumentException("Invoice ID can't be null");
    }
    return invoiceDao.findById(id).orElseThrow(() -> new InvoiceNotFoundException("Invoice with ID " + id + " not found"));
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
