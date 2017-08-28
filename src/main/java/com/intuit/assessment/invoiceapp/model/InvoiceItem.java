package com.intuit.assessment.invoiceapp.model;

import lombok.Data;

@Data
public class InvoiceItem {
	
	private Long itemId;
	
	private String description;
	
	private int amount;	
}
