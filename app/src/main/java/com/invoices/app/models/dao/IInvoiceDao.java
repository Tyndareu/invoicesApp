package com.invoices.app.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.invoices.app.models.entities.Invoice;

public interface IInvoiceDao extends JpaRepository<Invoice, Long> {

  
}