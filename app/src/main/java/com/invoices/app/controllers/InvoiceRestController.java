package com.invoices.app.controllers;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.invoices.app.models.dto.InvoiceDto;
import com.invoices.app.services.InvoiceService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/invoices")
public class InvoiceRestController {

  private final InvoiceService invoiceService;

  public InvoiceRestController(InvoiceService invoiceService) {
    this.invoiceService = invoiceService;
  }

  @GetMapping
  @Cacheable("invoices")
  public ResponseEntity<List<InvoiceDto>> getAllInvoices() {
    List<InvoiceDto> invoiceDtos = invoiceService.findAllInvoices();
    return ResponseEntity.ok(invoiceDtos);
  }

  @GetMapping("/{id}")
  public ResponseEntity<InvoiceDto> getInvoiceById(@NonNull @PathVariable Long id) {
    InvoiceDto invoiceDto = invoiceService.findInvoiceById(id);
    return ResponseEntity.ok(invoiceDto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<InvoiceDto> updateInvoice(@NonNull @PathVariable Long id,
      @Valid @NonNull @RequestBody InvoiceDto invoiceUpdate) {

    InvoiceDto invoiceDto = invoiceService.updateInvoice(id, invoiceUpdate);
    return ResponseEntity.ok(invoiceDto);
  }

  @PostMapping()
  public ResponseEntity<InvoiceDto> newInvoice(
      @NonNull @RequestParam Long customerId,
      @NonNull @Valid @RequestBody InvoiceDto newInvoiceDto) {

    InvoiceDto invoiceDto = invoiceService.newInvoice(customerId, newInvoiceDto);
    return ResponseEntity.ok(invoiceDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteInvoice(@NonNull @PathVariable Long id) {
    invoiceService.deleteInvoice(id);
    return ResponseEntity.noContent().build();

  }
}