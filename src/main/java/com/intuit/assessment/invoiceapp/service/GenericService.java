package com.intuit.assessment.invoiceapp.service;

import java.util.Collection;

public interface GenericService<T> {
	
	Collection<T> getAll();
	
	T findSpecific(Long id);
	
	T create(T t);
	
	T update(T t);
	
	boolean delete(Long id);
	
}
