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
    return new CustomersWithoutInvoicesDto(
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
        customer.getCreateAt());
  }

}