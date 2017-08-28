package com.intuit.assessment.invoiceapp.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.intuit.assessment.invoiceapp.model.InvoiceStatus;

import lombok.Data;

@Entity
@Data
public class Invoice {
	
	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
	private Long invoiceId;
	
	@OneToOne
	@JoinColumn(name="customerId")
	private Customer customer;
	
	private Date dueDate;
	
	@Enumerated(EnumType.STRING)
	private InvoiceStatus invoiceStatus;
	
	@Column(name="merchantId")
	private Long merchant;
	
	@OneToMany(mappedBy="invoice", cascade=CascadeType.ALL)
	private List<InvoiceItem> invoiceItems;

}
