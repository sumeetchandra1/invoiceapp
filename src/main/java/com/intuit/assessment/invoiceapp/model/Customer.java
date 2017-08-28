package com.intuit.assessment.invoiceapp.model;

import com.intuit.assessment.invoiceapp.model.Merchant;

import lombok.Data;

@Data
public class Customer {
	
	private Long customerId;
	
	private String name;
	
	private String email;
	
	private Invoice invoiceRequest;
	
	private Long merchant;

}
