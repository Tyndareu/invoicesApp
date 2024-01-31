package com.invoices.app.models.converter.entity;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.invoices.app.models.dto.ProductDto;
import com.invoices.app.models.entities.Product;

@Component
public class ProductToDtoProduct implements Converter<Product, ProductDto> {

  @Override
  public ProductDto convert(@NonNull Product product) {

    return ProductDto.builder()
        .id(product.getId())
        .name(product.getName())
        .description(product.getDescription())
        .price(product.getPrice())
        .createdAt(product.getCreatedAt())
        .build();

  }
}
