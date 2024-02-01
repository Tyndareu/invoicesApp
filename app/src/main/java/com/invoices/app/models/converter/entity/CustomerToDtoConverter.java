package com.invoices.app.models.converter.entity;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.invoices.app.models.dto.CustomerDto;
import com.invoices.app.models.dto.InvoiceDto;
import com.invoices.app.models.entities.Customer;

@Component
public class CustomerToDtoConverter implements Converter<Customer, CustomerDto> {

  @Override
  public CustomerDto convert(@NonNull Customer customer) {
    return CustomerDto.builder()
        .id(customer.getId())
        .name(customer.getName())
        .lastName(customer.getLastName())
        .address(customer.getAddress())
        .phone(customer.getPhone())
        .email(customer.getEmail())
        .nit(customer.getNit())
        .city(customer.getCity())
        .state(customer.getState())
        .country(customer.getCountry())
        .zip(customer.getZip())
        .createAt(customer.getCreateAt())
        .invoices(customer.getInvoices()
            .stream()
            .map(invoice -> InvoiceDto.builder()
                .id(invoice.getId())
                .description(invoice.getDescription())
                .observation(invoice.getObservation())
                .amount(invoice.calculateTotal())
                .status(invoice.getStatus())
                .createAt(invoice.getCreateAt())
                .build())
            .toList())
        .build();
  }

}