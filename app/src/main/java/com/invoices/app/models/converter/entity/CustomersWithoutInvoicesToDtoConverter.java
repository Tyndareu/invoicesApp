package com.invoices.app.models.converter.entity;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.invoices.app.models.dto.CustomersWithoutInvoicesDto;
import com.invoices.app.models.entities.Customer;

@Component
public class CustomersWithoutInvoicesToDtoConverter implements Converter<Customer, CustomersWithoutInvoicesDto> {

  @Override
  public CustomersWithoutInvoicesDto convert(@NonNull Customer customer) {
    return CustomersWithoutInvoicesDto.builder()
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
        .build();
  }

}