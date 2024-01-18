package com.invoices.app.models.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.invoices.app.models.dao.IProductDao;
import com.invoices.app.models.dto.ProductsDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

  private static final String notFound = " not found";

  private final IProductDao productDao;

  @Transactional(readOnly = true)
  public List<ProductsDto> findAllProducts() {
    return productDao.findAll()
        .stream()
        .map(ProductsDto::new)
        .toList();
  }

}
