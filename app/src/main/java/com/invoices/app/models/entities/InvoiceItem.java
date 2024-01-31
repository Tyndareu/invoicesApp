package com.invoices.app.models.entities;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "invoice_items")
public class InvoiceItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "invoice_id")
  private Invoice invoice;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  private BigDecimal price;

  private BigDecimal discount;

  private Integer quantity;

  public BigDecimal calculateTotal() {
    BigDecimal subtotal = price.multiply(BigDecimal.valueOf(quantity));
    BigDecimal discountPercentage = discount.divide(new BigDecimal("100"));
    BigDecimal discountAmount = subtotal.multiply(discountPercentage);
    return subtotal.subtract(discountAmount);

  }

}