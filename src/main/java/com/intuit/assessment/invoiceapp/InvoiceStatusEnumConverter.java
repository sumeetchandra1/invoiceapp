package com.intuit.assessment.invoiceapp;

import java.beans.PropertyEditorSupport;

import com.intuit.assessment.invoiceapp.model.InvoiceStatus;

public class InvoiceStatusEnumConverter extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {

    	InvoiceStatus invoiceStatus = InvoiceStatus.valueOf(text.toUpperCase());
        setValue(invoiceStatus);
    }
}
