package com.invoices.app.models.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.invoices.app.models.entities.Customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

  public CustomerDto(Customer customer) {
    this.id = customer.getId();
    this.name = customer.getName();
    this.lastName = customer.getLastName();
    this.address = customer.getAddress();
    this.phone = customer.getPhone();
    this.email = customer.getEmail();
    this.nit = customer.getNit();
    this.city = customer.getCity();
    this.state = customer.getState();
    this.country = customer.getCountry();
    this.zip = customer.getZip();
    this.createAt = customer.getCreateAt();
    this.invoices = customer.getInvoices()
        .stream()
        .map(InvoiceDto::new)
        .collect(Collectors.toList());
  }
}