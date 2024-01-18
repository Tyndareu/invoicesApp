package com.invoices.app.models.dto;

import com.invoices.app.models.entities.Invoice;

import lombok.Data;

@Data
public class InvoiceDto {
  private Long id;
  private String description;
  private String observation;
  private Double amount;
  private Boolean status;
  private String createAt;
  private CustomerNotInvoicesDto customer;

  public InvoiceDto(Invoice invoice) {
    this.id = invoice.getId();
    this.description = invoice.getDescription();
    this.observation = invoice.getObservation();
    this.amount = invoice.getAmount();
    this.status = invoice.getStatus();
    this.createAt = invoice.getCreateAt().toString();
    this.customer = new CustomerNotInvoicesDto(invoice.getCustomer());
  }

}
