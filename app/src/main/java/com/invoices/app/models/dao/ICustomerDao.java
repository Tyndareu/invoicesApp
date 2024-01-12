package com.invoices.app.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.invoices.app.models.entities.Customer;

public interface ICustomerDao extends JpaRepository<Customer, Long> {

}
