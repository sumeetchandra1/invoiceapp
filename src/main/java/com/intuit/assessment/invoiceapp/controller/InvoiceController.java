package com.intuit.assessment.invoiceapp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.assessment.invoiceapp.entity.Customer;
import com.intuit.assessment.invoiceapp.entity.Invoice;
import com.intuit.assessment.invoiceapp.entity.InvoiceItem;
import com.intuit.assessment.invoiceapp.model.InvoiceRequest;
import com.intuit.assessment.invoiceapp.service.CustomerServiceImpl;
import com.intuit.assessment.invoiceapp.service.InvoiceServiceImpl;

@RestController
@RequestMapping(path = "/invoice")
public class InvoiceController {

	private static final Logger LOGGER = LogManager.getLogger(InvoiceController.class);

	@Autowired
	CustomerServiceImpl customerServiceImpl;

	@Autowired
	InvoiceServiceImpl invoiceServiceImpl;
	
	/**
	 * This method handles invoice submission. For customer and invoice both create and update operations
	 * are supported
	 *
	 * @param  invoiceRequestObject  Captures the JSON formatted incoming request object
	 * 
	 * @return ResponseEntity<InvoiceRequest>  Returns response enhanced with id information entities persisted
	 */
	@RequestMapping(value = "/submit", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<InvoiceRequest> submitInvoice(@RequestBody InvoiceRequest invoiceRequestObject) {
		
		logData(invoiceRequestObject, "Incoming Request is: ");
        
		Customer customer = null;
		customer = handleCustomerUpdateAndCreate(invoiceRequestObject, customer);

		Invoice invoice = null;
		Invoice createdInvoice = handleInvoiceUpdateAndCreate(invoiceRequestObject, customer, invoice);
		
		enhanceResponse(invoiceRequestObject, createdInvoice, customer);
		logData(invoiceRequestObject, "Outgoing Response is: ");

		return new ResponseEntity<InvoiceRequest>(invoiceRequestObject, HttpStatus.CREATED);

	}
	
	
	/**
	 * Helper method to handle both updating a customer and creating a customer
	 * 
	 *
	 * @param  invoiceRequestObject  Captures the JSON formatted incoming request object
	 * @param  customer              Customer entity instance used for handling customer update/create 
	 * @return Customer  			 Returns created or updated customer entity object
	 */
	private Customer handleCustomerUpdateAndCreate(InvoiceRequest invoiceRequestObject, Customer customer) {
		
		//Get existing customer if customer id is sent in the incoming request
		if (invoiceRequestObject.getCustomer().getCustomerId() != null) {
			customer = customerServiceImpl.findSpecific(invoiceRequestObject.getCustomer().getCustomerId());
		}

		//If its an invoice request for a new customer
		if (customer == null) {
			
			customer = new Customer();
			
			//Copy customer properties from incoming request into Customer entity
			BeanUtils.copyProperties(invoiceRequestObject.getCustomer(), customer);
			
			//Save customer entity to database
			customer = (Customer) customerServiceImpl.create(customer);
			
		} else {
			
			//If its an invoice request for existing customer
			//Copy customer properties from incoming request to existing customer entity
			BeanUtils.copyProperties(invoiceRequestObject.getCustomer(), customer);
			
			//Update existing customer
			customer = customerServiceImpl.update(customer);
		}
		
		return customer;
	}
	
	
	/**
	 * Helper method to handle both updating and creating an invoice
	 * 
	 *
	 * @param  invoiceRequestObject  Captures the JSON formatted incoming request object
	 * @param  customer              Customer entity instance used for adding created/updated Invoice to
	 * @param  invoice               Invoice entity instance used for handling invoice update/create
	 * @return Invoice  			 Returns created or updated invoice entity object
	 */
	private Invoice handleInvoiceUpdateAndCreate(InvoiceRequest invoiceRequestObject, Customer customer, Invoice invoice) {
		
	    //If request is an update for existing invoice, then fetch existing invoice from db
		if(invoiceRequestObject.getCustomer().getInvoiceRequest().getInvoiceId() != null)
			invoice = invoiceServiceImpl.findSpecific(invoiceRequestObject.getCustomer().getInvoiceRequest().getInvoiceId());
		else {
			
			//Else if its a new Invoice, create entity object and add to customer entity
			invoice = new Invoice();
			invoice.setInvoiceItems(new ArrayList<InvoiceItem>());
			invoice.setCustomer(customer);
		}

		//Copy Invoice properties from object in incoming request to entity Invoice
		BeanUtils.copyProperties(invoiceRequestObject.getCustomer().getInvoiceRequest(), invoice);
		
		com.intuit.assessment.invoiceapp.model.Invoice invoiceRequest = invoiceRequestObject.getCustomer().getInvoiceRequest();
	    //Create another list to store all the invoice items extracted from incoming request
		//This is needed to avoid concurrent modification exception
		List<InvoiceItem> invoiceItemEntityList = new ArrayList<InvoiceItem>();
		
		//Iterate over invoice items in the incoming request, copy their properties to Invoice Item entity
		//Set Invoice entity instance for each of these Invoice item entities 
		for(com.intuit.assessment.invoiceapp.model.InvoiceItem item : invoiceRequest.getInvoiceItems()) {
			InvoiceItem invoiceItem = new InvoiceItem();
			BeanUtils.copyProperties(item, invoiceItem);
			invoiceItem.setInvoice(invoice);
			invoiceItemEntityList.add(invoiceItem);
			
		}
		
		//Add all invoice items to Invoice
		invoice.setInvoiceItems(invoiceItemEntityList);

		//Save new invoice
		Invoice createdInvoice = (Invoice) invoiceServiceImpl.create(invoice);
		
		return createdInvoice;
		
	}
	
	/**
	 * Helper method to enhance incoming request object with Ids of entities created
	 * 
	 *
	 * @param  invoiceRequestObject  Captures the JSON formatted incoming request object which is to be enhanced
	 * @param  customer              Customer entity instance used for setting customer id in request object
	 * @param  createdInvoice        Invoice entity instance used for setting invoice and invoice item ids in request 
	 * 
	 */
	private void enhanceResponse(InvoiceRequest invoiceRequestObject, Invoice createdInvoice, Customer customer) {
		
		invoiceRequestObject.getCustomer().getInvoiceRequest().setInvoiceId(createdInvoice.getInvoiceId());
		
		invoiceRequestObject.getCustomer().setCustomerId(customer.getCustomerId());
		
		int i=0;
		
		for(InvoiceItem item: createdInvoice.getInvoiceItems()) {
			
			invoiceRequestObject.getCustomer().getInvoiceRequest().getInvoiceItems().get(i).setItemId(item.getItemId());
			i++;
		}
	}
	
	
	/**
	 * Helper method to log incoming request and outgoing response in pretty print JSON format 
	 * 
	 *
	 * @param  thingToBeLogged       Captures the JSON formatted incoming request object which is to be pretty printed
	 * @param  message               Message string to be used along with logging request and responses
	 * 
	 */
	private void logData(InvoiceRequest thingToBeLogged, String message) {
		        
        ObjectMapper mapper = new ObjectMapper();
        String prettyPrinted = null;
		
        try {
        	prettyPrinted = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(thingToBeLogged);
		} catch (JsonProcessingException e) {
	
			e.printStackTrace();
		}
        
        LOGGER.info(message + prettyPrinted);
	}

}
