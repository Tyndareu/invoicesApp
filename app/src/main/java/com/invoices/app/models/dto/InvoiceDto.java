package com.invoices.app.models.dto;

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

  public InvoiceDto(Invoice invoice) {
    this.id = invoice.getId();
    this.description = invoice.getDescription();
    this.observation = invoice.getObservation();
    this.amount = invoice.getAmount();
    this.status = invoice.getStatus();
    this.createAt = invoice.getCreateAt().toString();
    this.customer = new CustomersWithoutInvoicesDto(invoice.getCustomer());
  }

}
