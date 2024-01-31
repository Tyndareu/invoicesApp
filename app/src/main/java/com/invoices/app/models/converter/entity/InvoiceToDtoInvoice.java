package com.invoices.app.models.converter.entity;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.invoices.app.models.dto.InvoiceDto;
import com.invoices.app.models.entities.Invoice;

@Component
public class InvoiceToDtoInvoice implements Converter<Invoice, InvoiceDto> {

  @Override
  public InvoiceDto convert(@NonNull Invoice invoice) {

    return InvoiceDto.builder()
        .id(invoice.getId())
        .description(invoice.getDescription())
        .observation(invoice.getObservation())
        .amount(invoice.getAmount())
        .status(invoice.getStatus())
        .createAt(invoice.getCreateAt())
        .build();
  }
}
