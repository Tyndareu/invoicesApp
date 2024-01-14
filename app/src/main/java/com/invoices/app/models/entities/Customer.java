package com.invoices.app.models.entities;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
@Table(name = "customers")
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotEmpty(message = "The name can't be empty")
  @Column(nullable = false)
  private String name;

  @Column(name = "last_name")
  private String lastName;

  private String address;

  private String phone;

  @Email(message = "The email must be a valid email address and unique")
  @Column(unique = true)
  private String email;

  private String nit;

  private String city;

  private String state;

  private String country;

  private String zip;

  @Column(name = "created_at")
  @Temporal(TemporalType.DATE)
  private Date createAt;

  @PrePersist
  public void prePersist() {
    this.createAt = new Date();
  }

  @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
  @JsonIgnoreProperties("customer")
  private List<Invoice> invoices;

  public void copyFrom(Customer customerCopy) {
    this.setName(customerCopy.getName());
    this.setLastName(customerCopy.getLastName());
    this.setEmail(customerCopy.getEmail());
    this.setAddress(customerCopy.getAddress());
    this.setPhone(customerCopy.getPhone());
    this.setNit(customerCopy.getNit());
    this.setCity(customerCopy.getCity());
    this.setState(customerCopy.getState());
    this.setCountry(customerCopy.getCountry());
    this.setZip(customerCopy.getZip());
}
}
