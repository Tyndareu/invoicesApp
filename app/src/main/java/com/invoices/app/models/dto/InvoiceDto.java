package com.invoices.app.models.dto;

import java.util.List;

import com.invoices.app.models.entities.Invoice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceDto {
  private Long id;
  private String description;
  private String observation;
  private Double amount;
  private Boolean status;
  private String createAt;
  private CustomersWithoutInvoicesDto customer;
  private List<InvoiceItemDto> items;

  public InvoiceDto(Invoice invoice) {
    this.id = invoice.getId();
    this.description = invoice.getDescription();
    this.observation = invoice.getObservation();
    this.amount = invoice.calculateTotal();
    this.status = invoice.getStatus();
    this.createAt = invoice.getCreateAt().toString();
    this.customer = new CustomersWithoutInvoicesDto(invoice.getCustomer());
    this.items = invoice.getItems().stream().map(InvoiceItemDto::new).toList();
  }
}
