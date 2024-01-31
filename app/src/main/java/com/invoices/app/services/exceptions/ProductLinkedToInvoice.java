package com.invoices.app.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ProductLinkedToInvoice extends RuntimeException {

  public ProductLinkedToInvoice(String message) {
    super(message);
  }

}
