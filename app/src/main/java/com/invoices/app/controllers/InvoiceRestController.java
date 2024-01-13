package com.invoices.app.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.invoices.app.models.entities.Invoice;
import com.invoices.app.models.services.invoice.IInvoiceService;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/invoices")
public class InvoiceRestController {

  private final IInvoiceService invoiceService;

  public InvoiceRestController(IInvoiceService invoiceService) {
    this.invoiceService = invoiceService;
  }

  @GetMapping
  public List<Invoice> getAllCustomers() {
    return invoiceService.findAllInvoices();
  }

}
