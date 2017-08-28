package com.intuit.assessment.invoiceapp.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Merchant {
	
	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
	private Long merchantId;

}
