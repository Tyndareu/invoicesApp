package com.invoices.app.models.converter.dto;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.invoices.app.models.dto.CustomerDto;
import com.invoices.app.models.entities.Customer;
import com.invoices.app.models.entities.Invoice;

@Component
public class DtoToCustomerConverter implements Converter<CustomerDto, Customer> {

  @Override
  public Customer convert(@NonNull CustomerDto customerDto) {
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
        .invoices(customerDto.getInvoices()
            .stream()
            .map(invoiceDto -> Invoice.builder()
                .id(invoiceDto.getId())
                .description(invoiceDto.getDescription())
                .observation(invoiceDto.getObservation())
                .status(invoiceDto.getStatus())
                .createAt(invoiceDto.getCreateAt())
                .build())
            .toList())
        .build();
  }

}