package com.invoices.app.models.dto;

import java.util.Date;

import com.invoices.app.models.entities.Product;

import lombok.Value;

@Value
public class ProductsDto {

  private Long id;
  private String name;
  private String description;
  private Double price;
  private Date createdAt;

  public ProductsDto(Product product) {
    this.id = product.getId();
    this.name = product.getName();
    this.description = product.getDescription();
    this.price = product.getPrice();
    this.createdAt = product.getCreatedAt();

  }

}