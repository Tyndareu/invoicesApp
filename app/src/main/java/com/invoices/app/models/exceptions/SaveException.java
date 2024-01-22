package com.invoices.app.models.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class SaveException extends RuntimeException {

  public SaveException(String message) {
    super(message);
  }

  public SaveException(String message, Throwable cause) {
    super(message, cause);
  }

}