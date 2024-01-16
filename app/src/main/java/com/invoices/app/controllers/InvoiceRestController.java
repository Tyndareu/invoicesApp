package com.invoices.app.controllers;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
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
import com.invoices.app.models.entities.Invoice;
import com.invoices.app.models.services.InvoiceService;

import jakarta.validation.Valid;

import java.util.stream.Collectors;

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
  public ResponseEntity<List<InvoiceDto>> getAllCustomers() {
    List<InvoiceDto> invoicesDto = invoiceService.findAllInvoices()
    .stream()
    .map(InvoiceDto::new)
    .collect(Collectors.toList());

    return !invoicesDto.isEmpty() ? ResponseEntity.ok(invoicesDto) : ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<InvoiceDto> getInvoiceById(@PathVariable Long id) {

   InvoiceDto invoiceDto = new InvoiceDto(invoiceService.findInvoiceById(id));
    return ResponseEntity.ok(invoiceDto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<InvoiceDto> updateInvoice(@PathVariable Long id, @Valid @RequestBody Invoice invoiceUpdate) {

    if (id == null) {
      return ResponseEntity.badRequest().build();
    }

    Invoice invoice = invoiceService.findInvoiceById(id);
    invoice.copyFrom(invoiceUpdate);
    invoiceService.updateInvoice(invoice);
    InvoiceDto invoiceDto = new InvoiceDto(invoice);

    return ResponseEntity.ok(invoiceDto);
  }

  @PostMapping()
  public ResponseEntity<InvoiceDto> createInvoice(@RequestParam Long customerId, @Valid @RequestBody Invoice newInvoice) {
    invoiceService.newInvoice(customerId, newInvoice);
    InvoiceDto newInvoiceDto = new InvoiceDto(newInvoice);
    return ResponseEntity.ok(newInvoiceDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
    invoiceService.deleteInvoice(id);
    return ResponseEntity.noContent().build();
  }
}