package com.invoices.app.models.converter.entity;

import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.invoices.app.models.dto.CustomerDto;
import com.invoices.app.models.dto.InvoiceDto;
import com.invoices.app.models.entities.Customer;

@Component
public class CustomersToDtoConverter implements Converter<Customer, CustomerDto> {

  @Override
  public CustomerDto convert(@NonNull Customer customer) {
    return new CustomerDto(
        customer.getId(),
        customer.getName(),
        customer.getLastName(),
        customer.getAddress(),
        customer.getPhone(),
        customer.getEmail(),
        customer.getNit(),
        customer.getCity(),
        customer.getState(),
        customer.getCountry(),
        customer.getZip(),
        customer.getCreateAt(),
        customer.getInvoices()
            .stream()
            .map(InvoiceDto::new)
            .collect(Collectors.toList()));
  }

}