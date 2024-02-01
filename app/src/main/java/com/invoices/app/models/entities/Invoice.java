package com.invoices.app.models.entities;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "invoices")
public class Invoice {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String description;

  private String observation;

  private Boolean status;

  @Column(name = "created_at")
  @Temporal(TemporalType.DATE)
  private Date createAt;

  @ManyToOne
  @JsonIgnoreProperties("invoices")
  @JoinColumn(name = "customer_id")
  private Customer customer;

  @OneToMany(mappedBy = "invoice", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JsonIgnoreProperties({ "invoice", "customer" })
  private List<InvoiceItem> items;

  @PrePersist
  public void prePersist() {
    this.createAt = new Date();
  }

  public double calculateTotal() {
    double invoiceTotal = 0;
    for (InvoiceItem item : this.getItems()) {
      double itemTotal = item.calculateTotal().doubleValue();
      invoiceTotal += itemTotal;
    }
    return invoiceTotal;
  }

}