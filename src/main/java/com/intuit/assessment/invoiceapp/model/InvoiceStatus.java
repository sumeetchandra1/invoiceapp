package com.intuit.assessment.invoiceapp.model;

public enum InvoiceStatus {
	
	PENDING("Pending"),
	OVERDUE("Overdue"),
	CANCELLED("Cancelled"),
	PAID("Paid");
	
	private String invoiceStatus;
	 
    private InvoiceStatus(String value) {
    	this.invoiceStatus = value;
    }
	 
	public String getInvoiceStatusValue() {
	    return invoiceStatus;
	}

}
