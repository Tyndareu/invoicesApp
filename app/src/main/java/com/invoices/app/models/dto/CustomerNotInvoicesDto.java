package com.invoices.app.models.dto;

import java.util.Date;

import com.invoices.app.models.entities.Customer;

import lombok.Data;

@Data
public class CustomerNotInvoicesDto {

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

  public CustomerNotInvoicesDto() {
  }

  public CustomerNotInvoicesDto(Customer customer) {
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
  }

  public CustomerNotInvoicesDto(CustomerDto customerDto) {
    this.id = customerDto.getId();
    this.name = customerDto.getName();
    this.lastName = customerDto.getLastName();
    this.address = customerDto.getAddress();
    this.phone = customerDto.getPhone();
    this.email = customerDto.getEmail();
    this.nit = customerDto.getNit();
    this.city = customerDto.getCity();
    this.state = customerDto.getState();
    this.country = customerDto.getCountry();
    this.zip = customerDto.getZip();
    this.createAt = customerDto.getCreateAt();
  }
}