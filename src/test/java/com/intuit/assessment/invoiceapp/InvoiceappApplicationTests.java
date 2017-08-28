package com.intuit.assessment.invoiceapp;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.intuit.assessment.invoiceapp.model.Invoice;
import com.intuit.assessment.invoiceapp.model.InvoiceItem;
import com.intuit.assessment.invoiceapp.model.InvoiceRequest;
import com.intuit.assessment.invoiceapp.model.InvoiceStatus;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InvoiceappApplicationTests {

	@Test
	public void contextLoads() {
	}
	
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void createClient() {

    	InvoiceRequest invoiceRequest = createInvoiceRequestObject();
    	
        ResponseEntity<InvoiceRequest> responseEntity =

            restTemplate.postForEntity("/invoice/submit", invoiceRequest, InvoiceRequest.class);

        InvoiceRequest invoiceResponse = responseEntity.getBody();

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        assertEquals(invoiceRequest.getCustomer().getEmail(), invoiceResponse.getCustomer().getEmail());
        assertEquals(invoiceRequest.getCustomer().getName(), invoiceResponse.getCustomer().getName());
        assertEquals(invoiceRequest.getCustomer().getInvoiceRequest().getDueDate(),
        		invoiceResponse.getCustomer().getInvoiceRequest().getDueDate());
        assertEquals(invoiceRequest.getCustomer().getInvoiceRequest().getInvoiceStatus(),
        		invoiceResponse.getCustomer().getInvoiceRequest().getInvoiceStatus());
        assertEquals(invoiceRequest.getCustomer().getInvoiceRequest().getDueDate(),
        		invoiceResponse.getCustomer().getInvoiceRequest().getDueDate());

    }
    
    private InvoiceRequest createInvoiceRequestObject() {
    	
    	InvoiceRequest invoiceRequest = new InvoiceRequest();
    	
    	invoiceRequest.getCustomer().setEmail("abc@gmail.com");
    	invoiceRequest.getCustomer().setName("John");
    	invoiceRequest.getCustomer().getInvoiceRequest().setDueDate(Date.valueOf("2017-10-17"));
    	invoiceRequest.getCustomer().getInvoiceRequest().setInvoiceStatus(InvoiceStatus.PENDING);
    	invoiceRequest.getCustomer().getInvoiceRequest().setMerchant(1L);
    	
    	InvoiceItem invoiceItem1 = new InvoiceItem();
    	
    	invoiceItem1.setAmount(10);
    	invoiceItem1.setDescription("plumbing");
    	
    	InvoiceItem invoiceItem2 = new InvoiceItem();
    	
    	invoiceItem2.setAmount(200);
    	invoiceItem2.setDescription("flooring");
    	
    	List<InvoiceItem> invoiceItems = new ArrayList<InvoiceItem>();
    	
    	invoiceItems.add(invoiceItem1);
    	invoiceItems.add(invoiceItem2);
    	
    	invoiceRequest.getCustomer().getInvoiceRequest().setInvoiceItems(invoiceItems);
    	
    	invoiceRequest.getCustomer().setMerchant(1L);
    	
    	return invoiceRequest;
    	
    }

}
