package com.invoices.app.models.converter.dto;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.invoices.app.models.dto.InvoiceDto;
import com.invoices.app.models.entities.Invoice;

@Component
public class DtoToInvoiceConverter implements Converter<InvoiceDto, Invoice> {

  @Override
  public Invoice convert(@NonNull InvoiceDto invoiceDto) {

    return Invoice.builder()
        .id(invoiceDto.getId())
        .description(invoiceDto.getDescription())
        .observation(invoiceDto.getObservation())
        .amount(invoiceDto.getAmount())
        .status(invoiceDto.getStatus())
        .createAt(invoiceDto.getCreateAt())
        .build();
  }

}
