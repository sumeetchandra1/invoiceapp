package com.intuit.assessment.invoiceapp.model;

import lombok.Data;

@Data
public class InvoiceRequest {

	private Customer customer;

	@Override
	public String toString() {
		return "InvoiceRequest [customer=" + customer + "]";
	}
}
