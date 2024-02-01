package com.invoices.app.services;

import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.invoices.app.models.dto.InvoiceItemDto;
import com.invoices.app.models.entities.InvoiceItem;
import com.invoices.app.models.repository.InvoiceItemRepository;
import com.invoices.app.services.exceptions.NotFoundException;
import com.invoices.app.services.exceptions.SaveException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvoiceItemService {

  private static final String notFound = " not found";
  private static final String invoiceId = "Invoice item with ID ";
  private static final String errorSaving = "Error saving invoice item: Unable to save invoice item information";

  private final InvoiceItemRepository invoiceItemRepository;
  private final ConversionService conversionService;

  @Transactional
  public InvoiceItemDto updateInvoiceItem(@NonNull Long id, @NonNull InvoiceItemDto invoiceItemDto) {
    InvoiceItem existingInvoiceItem = this.invoiceItemRepository.findById(id)
        .orElseThrow(() -> new NotFoundException(invoiceId + id + notFound));

    existingInvoiceItem.setProduct(invoiceItemDto.getProduct());
    existingInvoiceItem.setPrice(invoiceItemDto.getPrice());
    existingInvoiceItem.setQuantity(invoiceItemDto.getQuantity());
    existingInvoiceItem.setDiscount(invoiceItemDto.getDiscount());

    try {
      existingInvoiceItem = this.invoiceItemRepository.save(existingInvoiceItem);
    } catch (DataIntegrityViolationException e) {
      throw new SaveException(errorSaving, e);
    }

    return this.conversionService.convert(existingInvoiceItem, InvoiceItemDto.class);
  }

  @Transactional
  public InvoiceItemDto newInvoiceItem(@NonNull InvoiceItemDto invoiceItemDto) {

    InvoiceItem invoiceItem = this.conversionService.convert(invoiceItemDto, InvoiceItem.class);

    if (invoiceItem == null) {
      throw new SaveException(errorSaving, null);
    }
    try {
      invoiceItem = this.invoiceItemRepository.save(invoiceItem);
      return this.conversionService.convert(invoiceItem, InvoiceItemDto.class);
    } catch (DataIntegrityViolationException e) {
      throw new SaveException(errorSaving, e);
    }
  }

  @Transactional
  public void deleteInvoiceItem(@NonNull Long id) {
    this.invoiceItemRepository.deleteById(id);
  }

}
