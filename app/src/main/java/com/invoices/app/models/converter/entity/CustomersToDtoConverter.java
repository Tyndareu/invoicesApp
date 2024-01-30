package com.invoices.app.models.converter.entity;

import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.invoices.app.models.dto.CustomerDto;
import com.invoices.app.models.dto.InvoiceDto;
import com.invoices.app.models.entities.Customer;

@Component
public class CustomersToDtoConverter implements Converter<Customer, CustomerDto> {

  private final ConversionService conversionService;

  public CustomersToDtoConverter(ConversionService conversionService) {
    this.conversionService = conversionService;
  }

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
            .map(invoice -> this.conversionService.convert(invoice, InvoiceDto.class))
            .toList())
        .build();
  }

}