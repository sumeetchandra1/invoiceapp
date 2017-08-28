package com.intuit.assessment.invoiceapp.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="INVOICE_ITEM")
@Data
public class InvoiceItem {
	
	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="ITEM_ID")
	private Long itemId;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="amount")
	private int amount;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private Invoice invoice;
	
	

}
