package com.intuit.assessment.invoiceapp.model;

import java.util.List;


import com.intuit.assessment.invoiceapp.model.Customer;
import com.intuit.assessment.invoiceapp.model.Invoice;

import lombok.Data;

@Data
public class Merchant {
	
	private Long merchantId;
	
	public Merchant(Long id) {
		this.merchantId = id;
	}
	
}
