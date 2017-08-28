package com.intuit.assessment.invoiceapp.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.intuit.assessment.invoiceapp.model.InvoiceItem;
import com.intuit.assessment.invoiceapp.model.Merchant;

import lombok.Data;

@Data
public class Invoice {

	private Long invoiceId;
	
	private Date dueDate;
	
	private InvoiceStatus invoiceStatus;
	
	private Long merchant;
	
	private List<InvoiceItem> invoiceItems = new ArrayList<>();
}
