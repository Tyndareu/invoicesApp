package com.invoices.app.models.dto;

import java.util.Date;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class CustomerDto {
  private Long id;
  private String name;
  private String lastName;
  private String address;
  private String phone;
  private String email;
  private String nit;
  private String city;
  private String state;
  private String country;
  private String zip;
  private Date createAt;
  private List<InvoiceDto> invoices;
}