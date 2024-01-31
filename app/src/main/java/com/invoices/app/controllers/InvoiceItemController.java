package com.invoices.app.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.invoices.app.models.dto.InvoiceItemDto;
import com.invoices.app.services.InvoiceItemService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/invoiceitem")
public class InvoiceItemController {

  private final InvoiceItemService invoiceItemService;

  public InvoiceItemController(InvoiceItemService invoiceItemService) {
    this.invoiceItemService = invoiceItemService;

  }

  @PutMapping("{id}")
  public ResponseEntity<InvoiceItemDto> updateInvoiceItem(@NonNull @PathVariable Long id,
      @NonNull @Valid @RequestBody InvoiceItemDto invoiceItemDto) {

    InvoiceItemDto updatedInvoiceItem = this.invoiceItemService.updateInvoiceItem(id, invoiceItemDto);
    return ResponseEntity.ok(updatedInvoiceItem);

  }

  @PostMapping()
  public ResponseEntity<InvoiceItemDto> newInvoiceItem(@NonNull @Valid @RequestBody InvoiceItemDto invoiceItemDto) {

    InvoiceItemDto newInvoiceItem = this.invoiceItemService.newInvoiceItem(invoiceItemDto);
    return ResponseEntity.ok(newInvoiceItem);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> deleteInvoiceItem(@NonNull @PathVariable Long id) {
    this.invoiceItemService.deleteInvoiceItem(id);
    return ResponseEntity.noContent().build();
  }

}
