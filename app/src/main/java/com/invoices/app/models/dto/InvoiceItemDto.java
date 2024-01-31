package com.invoices.app.models.dto;

import java.math.BigDecimal;

import com.invoices.app.models.entities.Invoice;
import com.invoices.app.models.entities.InvoiceItem;
import com.invoices.app.models.entities.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceItemDto {

  private Long id;
  private Product product;
  private BigDecimal price;
  private BigDecimal discount;
  private Integer quantity;
  private BigDecimal total;
  private Invoice invoice;

  // public InvoiceItemDto(InvoiceItem invoiceItem) {
  // this.id = invoiceItem.getId();
  // this.product = invoiceItem.getProduct();
  // this.price = invoiceItem.getPrice();
  // this.quantity = invoiceItem.getQuantity();
  // this.discount = invoiceItem.getDiscount();
  // this.total = invoiceItem.calculateTotal();
  // }

}
