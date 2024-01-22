package com.invoices.app.models.converter.dto;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.invoices.app.models.dto.CustomersWithoutInvoicesDto;
import com.invoices.app.models.entities.Customer;

@Component
public class DtoToCustomerWithoutInvoicesConverter
    implements Converter<CustomersWithoutInvoicesDto, Customer> {

  @Override
  public Customer convert(@NonNull CustomersWithoutInvoicesDto dto) {
    return Customer.builder()
        .id(dto.getId())
        .name(dto.getName())
        .lastName(dto.getLastName())
        .address(dto.getAddress())
        .phone(dto.getPhone())
        .email(dto.getEmail())
        .nit(dto.getNit())
        .city(dto.getCity())
        .state(dto.getState())
        .country(dto.getCountry())
        .zip(dto.getZip())
        .createAt(dto.getCreateAt())
        .build();
  }
}
