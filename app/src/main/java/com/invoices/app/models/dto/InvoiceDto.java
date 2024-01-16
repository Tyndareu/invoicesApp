package com.invoices.app.models.dto;

import com.invoices.app.models.entities.Invoice;
import lombok.Data;

@Data
public class InvoiceDto {
  private Long id;
  private String description;
  private String observation;
  private Double amount;
  private String status;
  private String createAt;
  private CustomerDto customer;

  public InvoiceDto(Invoice invoice) {
    this.id = invoice.getId();
    this.description = invoice.getDescription();
    this.observation = invoice.getObservation();
    this.amount = invoice.getAmount();
    this.status = invoice.getStatus();
    this.createAt = invoice.getCreateAt().toString();
    this.customer = new CustomerDto(invoice.getCustomer());
  }
}
