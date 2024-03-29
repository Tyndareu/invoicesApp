package com.invoices.app.models.dto;

import java.math.BigDecimal;

import com.invoices.app.models.entities.Invoice;
import com.invoices.app.models.entities.Product;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class InvoiceItemDto {
  private Long id;
  private Product product;
  private BigDecimal price;
  private BigDecimal discount;
  private Integer quantity;
  private BigDecimal total;
  private Invoice invoice;
  private Double amount;
}
