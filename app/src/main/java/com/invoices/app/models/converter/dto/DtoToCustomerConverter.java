package com.invoices.app.models.converter.dto;

import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.invoices.app.models.dto.CustomerDto;
import com.invoices.app.models.entities.Customer;
import com.invoices.app.models.entities.Invoice;

@Component
public class DtoToCustomerConverter implements Converter<CustomerDto, Customer> {

  private final ConversionService conversionService;

  public DtoToCustomerConverter(ConversionService conversionService) {
    this.conversionService = conversionService;
  }

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
            .map(invoiceDto -> this.conversionService.convert(
                invoiceDto, Invoice.class))
            .toList())
        .build();
  }

}