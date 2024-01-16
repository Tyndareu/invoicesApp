package com.invoices.app.models.entities;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name = "invoices")
public class Invoice {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String description;

  private String observation;

  private Double amount;

  private String status;

  @Column(name = "create_at")
  @Temporal(TemporalType.DATE)
  private Date createAt;

  @PrePersist
  public void prePersist() {
    this.createAt = new Date();
  }

  @ManyToOne
  @JsonIgnoreProperties("invoices")
  @JoinColumn(name = "customer_id")
  private Customer customer;

  public void copyFrom(Invoice copyInvoice) {
    this.setDescription(copyInvoice.getDescription());
    this.setObservation(copyInvoice.getObservation());
    this.setAmount(copyInvoice.getAmount());
    this.setStatus(copyInvoice.getStatus());
  }
}