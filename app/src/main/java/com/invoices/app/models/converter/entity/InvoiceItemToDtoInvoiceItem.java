package com.invoices.app.models.converter.entity;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.invoices.app.models.dto.InvoiceItemDto;
import com.invoices.app.models.entities.InvoiceItem;

@Component
public class InvoiceItemToDtoInvoiceItem implements Converter<InvoiceItem, InvoiceItemDto> {

  @Override
  public InvoiceItemDto convert(@NonNull InvoiceItem invoiceItem) {
    return new InvoiceItemDto(
        invoiceItem.getId(),
        invoiceItem.getProduct(),
        invoiceItem.getPrice(),
        invoiceItem.getDiscount(),
        invoiceItem.getQuantity(),
        invoiceItem.calculateTotal());
  }
}
