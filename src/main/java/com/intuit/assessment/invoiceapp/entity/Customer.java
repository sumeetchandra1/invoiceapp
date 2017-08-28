package com.intuit.assessment.invoiceapp.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames = {"customerId", "email"})})
@Data
public class Customer {
	
	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
	private Long customerId;
	
	private String name;
	
	private String email;
	
	@Column(name="merchantId")
	private Long merchant;
	
	@OneToMany(mappedBy="customer")
	private List<Invoice> invoices;

}
