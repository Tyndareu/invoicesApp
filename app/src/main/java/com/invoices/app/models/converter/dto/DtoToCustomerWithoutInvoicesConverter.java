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
  public Customer convert(@NonNull CustomersWithoutInvoicesDto customerDto) {
    return Customer.builder()
        .id(customerDto.getId())
        .name(customerDto.getName())
        .lastName(customerDto.getLastName())
        .address(customerDto.getAddress())
        .phone(customerDto.getPhone())
        .email(customerDto.getEmail())
        .nit(customerDto.getNit())
        .city(customerDto.getCity())
        .state(customerDto.getState())
        .country(customerDto.getCountry())
        .zip(customerDto.getZip())
        .createAt(customerDto.getCreateAt())
        .build();
  }
}
