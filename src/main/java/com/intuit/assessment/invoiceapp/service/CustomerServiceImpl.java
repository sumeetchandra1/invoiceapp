package com.intuit.assessment.invoiceapp.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.intuit.assessment.invoiceapp.entity.Customer;
import com.intuit.assessment.invoiceapp.repository.CustomerRepository;

@Component
@Qualifier("CustomerServiceImpl")
public class CustomerServiceImpl implements GenericService<Customer> {

	private static final Logger LOGGER = LogManager.getLogger(CustomerServiceImpl.class);
	
	private static Map<Long, Customer> customerMap;
	private static Long id;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Override
	public Collection<Customer> getAll() {
		
		Collection<Customer> allCustomers = customerRepository.findAll();
		
		return allCustomers;
	}

	@Override
	public Customer findSpecific(Long id) {
		
		Customer customer = customerRepository.findOne(id);
		
		if(customer != null)
			return customer;
					
		return null;
	}

	@Override
	public Customer create(Customer customer) {
		
		if(customer.getCustomerId() != null) {
			LOGGER.error("customer cannot be created since ID value is specified");
			return null;
		}
		
		Customer createdCustomer = customerRepository.save(customer);
		
		return createdCustomer;
	}

	@Override
	public Customer update(Customer customer) {
		
		return customerRepository.save(customer);
	}

	@Override
	public boolean delete(Long id) {
		
		customerRepository.delete(id);
		
		return true;
		
	}
	
	
	public List<Customer> findNamesStartingWith(String name) {
		
		return customerRepository.findCustomersStartingWith(name);
	}
	
	

}
