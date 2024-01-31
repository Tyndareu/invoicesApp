package com.invoices.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.invoices.app.models.entities.InvoiceItem;

public interface IInvoiceItemDao extends JpaRepository<InvoiceItem, Long> {

  @Query("SELECT i FROM InvoiceItem i WHERE i.product.id = :productId")
  List<InvoiceItem> findByProductId(@Param("productId") Long productId);

}