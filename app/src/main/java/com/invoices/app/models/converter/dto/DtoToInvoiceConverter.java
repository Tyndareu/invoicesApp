package com.invoices.app.models.converter.dto;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.invoices.app.models.dto.InvoiceDto;
import com.invoices.app.models.entities.Customer;
import com.invoices.app.models.entities.Invoice;

@Component
public class DtoToInvoiceConverter implements Converter<InvoiceDto, Invoice> {

  @Override
  public Invoice convert(@NonNull InvoiceDto invoiceDto) {

    return Invoice.builder()
        .id(invoiceDto.getId())
        .description(invoiceDto.getDescription())
        .observation(invoiceDto.getObservation())
        .status(invoiceDto.getStatus())
        .createAt(invoiceDto.getCreateAt())
        .customer(Customer.builder()
            .id(invoiceDto.getCustomer().getId())
            .name(invoiceDto.getCustomer().getName())
            .lastName(invoiceDto.getCustomer().getLastName())
            .address(invoiceDto.getCustomer().getAddress())
            .phone(invoiceDto.getCustomer().getPhone())
            .email(invoiceDto.getCustomer().getEmail())
            .nit(invoiceDto.getCustomer().getNit())
            .city(invoiceDto.getCustomer().getCity())
            .state(invoiceDto.getCustomer().getState())
            .country(invoiceDto.getCustomer().getCountry())
            .zip(invoiceDto.getCustomer().getZip())
            .createAt(invoiceDto.getCustomer().getCreateAt())
            .build())
        .build();
  }

}
