package com.invoices.app.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.invoices.app.models.entities.InvoiceItem;

public interface IInvoiceItemDao extends JpaRepository<InvoiceItem, Long> {

}