package com.invoices.app.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.invoices.app.models.entities.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

}