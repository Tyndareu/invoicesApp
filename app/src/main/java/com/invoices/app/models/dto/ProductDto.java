package com.invoices.app.models.dto;

import java.util.Date;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class ProductDto {

  private Long id;
  private String name;
  private String description;
  private Double price;
  private Date createdAt;

  // public ProductDto(Product product) {
  // this.id = product.getId();
  // this.name = product.getName();
  // this.description = product.getDescription();
  // this.price = product.getPrice();
  // this.createdAt = product.getCreatedAt();

  // }

}
