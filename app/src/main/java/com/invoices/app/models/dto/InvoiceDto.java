package com.invoices.app.models.dto;

import java.util.Date;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class InvoiceDto {
  private Long id;
  private String description;
  private String observation;
  private Double amount;
  private Boolean status;
  private Date createAt;
  private CustomersWithoutInvoicesDto customer;
  private List<InvoiceItemDto> items;

}
// public InvoiceDto(Invoice invoice) {
// this.id = invoice.getId();
// this.description = invoice.getDescription();
// this.observation = invoice.getObservation();
// this.amount = invoice.calculateTotal();
// this.status = invoice.getStatus();
// this.createAt = invoice.getCreateAt();
// this.customer = invoice.getCustomer();
// this.items = invoice.getItems().stream().map(InvoiceItemDto::new).toList();
// }