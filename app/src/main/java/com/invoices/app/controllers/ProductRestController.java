package com.invoices.app.controllers;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.invoices.app.models.dto.ProductDto;
import com.invoices.app.services.ProductService;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/products")

public class ProductRestController {

  private final ProductService productService;

  public ProductRestController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  @Cacheable("products")
  public List<ProductDto> findAllProducts() {
    return productService.findAllProducts();
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductDto> findProductById(@NonNull @PathVariable Long id) {
    ProductDto productDto = productService.findProductById(id);
    return ResponseEntity.ok(productDto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProductDto> updatedProduct(@NonNull @PathVariable Long id,
      @NonNull @RequestBody ProductDto productDto) {
    ProductDto updatedProduct = productService.updateProduct(id, productDto);
    return ResponseEntity.ok(updatedProduct);
  }

  @PostMapping()
  public ResponseEntity<ProductDto> postMethodName(@NonNull @RequestBody ProductDto newProductDto) {
    ProductDto productDto = this.productService.newProduct(newProductDto);
    return ResponseEntity.ok(productDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProduct(@NonNull @PathVariable Long id) {
    productService.deleteProduct(id);
    return ResponseEntity.noContent().build();
  }

}