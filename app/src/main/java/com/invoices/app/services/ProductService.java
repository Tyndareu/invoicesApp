package com.invoices.app.services;

import java.util.List;

import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.invoices.app.models.dao.IProductDao;
import com.invoices.app.models.dto.ProductDto;
import com.invoices.app.models.entities.Product;
import com.invoices.app.services.exceptions.NotFoundException;
import com.invoices.app.services.exceptions.SaveException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

  private static final String saveError = "Error saving Product: Unable to save product information";
  private static final String notFound = " not found";

  private final IProductDao productDao;
  private final ConversionService conversionService;

  @Transactional(readOnly = true)
  public List<ProductDto> findAllProducts() {
    return this.productDao.findAll()
        .stream()
        .map(product -> this.conversionService.convert(product, ProductDto.class))
        .toList();
  }

  @Transactional(readOnly = true)
  public ProductDto findProductById(@NonNull Long id) {
    Product product = this.productDao.findById(id)
        .orElseThrow(() -> new NotFoundException("Product with ID " + id + notFound));
    return this.conversionService.convert(product, ProductDto.class);
  }

  @Transactional
  public ProductDto updateProduct(@NonNull Long id, @NonNull ProductDto productDto) {

    Product existingProduct = this.productDao.findById(id)
        .orElseThrow(() -> new NotFoundException("Product with ID " + id + notFound));

    existingProduct.setName(productDto.getName());
    existingProduct.setDescription(productDto.getDescription());
    existingProduct.setPrice(productDto.getPrice());

    try {
      existingProduct = this.productDao.save(existingProduct);
    } catch (DataIntegrityViolationException e) {
      throw new SaveException(saveError, e);
    }

    return this.conversionService.convert(existingProduct, ProductDto.class);
  }

  @Transactional
  public ProductDto newProduct(@NonNull ProductDto productDto) {
    Product product = this.conversionService.convert(productDto, Product.class);

    if (product == null) {
      throw new SaveException(saveError);
    }

    try {
      product = this.productDao.save(product);
      return this.conversionService.convert(product, ProductDto.class);

    } catch (DataIntegrityViolationException e) {
      throw new SaveException(saveError, e);
    }
  }

  public void deleteProduct(@NonNull Long id) {
    this.productDao.deleteById(id);
  }
}
