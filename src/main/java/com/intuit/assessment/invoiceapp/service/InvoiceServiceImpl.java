package com.intuit.assessment.invoiceapp.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.intuit.assessment.invoiceapp.entity.Invoice;
import com.intuit.assessment.invoiceapp.entity.InvoiceItem;
import com.intuit.assessment.invoiceapp.repository.InvoiceRepository;

@Qualifier("InvoiceServiceImpl")
@Component
public class InvoiceServiceImpl implements GenericService<Invoice> {

	private static final Logger LOGGER = LogManager.getLogger(InvoiceServiceImpl.class);
	
	@Autowired
	InvoiceRepository invoiceRepository;
	
	
	@Override
	public Collection<Invoice> getAll() {
		
		Collection<Invoice> allInvoices = invoiceRepository.findAll();
		
		return allInvoices;
	}

	@Override
	public Invoice findSpecific(Long id) {
		
		Invoice invoice = invoiceRepository.findOne(id);
		
		if(invoice != null)
			return invoice;
					
		return null;
	}

	@Override
	public Invoice create(Invoice invoice) {
				
		Invoice createdInvoice = invoiceRepository.save(invoice);
		
		return createdInvoice;
		
	}

	@Override
	public Invoice update(Invoice invoice) {
		
		Map invoiceItemsUpdateMap = new HashMap<Long, InvoiceItem>();
		
		Invoice invoiceToBeUpdated = findSpecific(invoice.getInvoiceId());
		
		if(invoiceToBeUpdated == null) {
			LOGGER.error("This customer does not exits. Update operation not possible !!");
			return null;
			
		}
		
		invoiceToBeUpdated.setDueDate(invoice.getDueDate()); 
		
		for(InvoiceItem updateItem : invoice.getInvoiceItems()) {
			
			invoiceItemsUpdateMap.put(updateItem.getItemId(), updateItem);
		}
		
		for(InvoiceItem item : invoiceToBeUpdated.getInvoiceItems()) {
			
			InvoiceItem itemWithUpdateInfo = (InvoiceItem)invoiceItemsUpdateMap.get(item.getItemId());
			
			item.setDescription(itemWithUpdateInfo.getDescription());
			item.setAmount(itemWithUpdateInfo.getAmount());
			
		}
		
		Invoice updatedInvoice = invoiceRepository.save(invoiceToBeUpdated);
		
		if(updatedInvoice != null)
			return updatedInvoice;
		
		return null;
	}

	@Override
	public boolean delete(Long id) {
		
		invoiceRepository.delete(id);
		
		return true;
	}

}
