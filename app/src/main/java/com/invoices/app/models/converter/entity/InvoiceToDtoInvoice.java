package com.invoices.app.models.converter.entity;

import java.util.Collections;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.invoices.app.models.dto.CustomersWithoutInvoicesDto;
import com.invoices.app.models.dto.InvoiceDto;
import com.invoices.app.models.dto.InvoiceItemDto;
import com.invoices.app.models.entities.Invoice;

@Component
public class InvoiceToDtoInvoice implements Converter<Invoice, InvoiceDto> {

  @Override
  public InvoiceDto convert(@NonNull Invoice invoice) {
    return InvoiceDto.builder()
        .id(invoice.getId())
        .description(invoice.getDescription())
        .observation(invoice.getObservation())
        .amount(invoice.calculateTotal())
        .status(invoice.getStatus())
        .createAt(invoice.getCreateAt())
        .customer(
            CustomersWithoutInvoicesDto.builder()
                .id(invoice.getCustomer().getId())
                .name(invoice.getCustomer().getName())
                .lastName(invoice.getCustomer().getLastName())
                .address(invoice.getCustomer().getAddress())
                .phone(invoice.getCustomer().getPhone())
                .email(invoice.getCustomer().getEmail())
                .nit(invoice.getCustomer().getNit())
                .city(invoice.getCustomer().getCity())
                .state(invoice.getCustomer().getState())
                .country(invoice.getCustomer().getCountry())
                .zip(invoice.getCustomer().getZip())
                .createAt(invoice.getCustomer().getCreateAt())
                .build())
        .items(invoice.getItems() != null ? invoice.getItems().stream()
            .map(invoiceItem -> InvoiceItemDto.builder()
                .id(invoiceItem.getId())
                .product(invoiceItem.getProduct())
                .price(invoiceItem.getPrice())
                .discount(invoiceItem.getDiscount())
                .quantity(invoiceItem.getQuantity())
                .total(invoiceItem.calculateTotal())
                .build())
            .toList() : Collections.emptyList())
        .build();
  }

}
