package com.invoices.app.models.converter.dto;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.invoices.app.models.dto.InvoiceItemDto;
import com.invoices.app.models.entities.InvoiceItem;

@Component
public class DtoToInvoiceItemConverter implements Converter<InvoiceItemDto, InvoiceItem> {

  @Override
  public InvoiceItem convert(@NonNull InvoiceItemDto dto) {
    return InvoiceItem.builder()
        .id(dto.getId())
        .price(dto.getPrice())
        .discount(dto.getDiscount())
        .quantity(dto.getQuantity())
        .product(dto.getProduct())
        .invoice(dto.getInvoice())
        .build();

  }

}