package com.invoices.app.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.invoices.app.models.entities.Invoice;
import com.invoices.app.models.services.invoice.IInvoiceService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

  @GetMapping("/{id}")
  public ResponseEntity<Invoice> getInvoiceById(@PathVariable Long id) {

    Invoice invoice = invoiceService.findInvoiceById(id);
    return ResponseEntity.ok(invoice);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Invoice> updateInvoice(@PathVariable Long id, @RequestBody Invoice invoiceUpdate) {

    Invoice invoice = invoiceService.findInvoiceById(id);

    invoice.copyFrom(invoiceUpdate);

    invoiceService.saveInvoice(invoice);

    return ResponseEntity.ok(invoice);

  }

}
