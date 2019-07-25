package com.ingbank.banking.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.ingbank.banking.entity.Customer;
import com.ingbank.banking.entity.Transaction;
import com.ingbank.banking.exception.ApplicationException;
import com.ingbank.banking.model.CustomerRequestModel;
import com.ingbank.banking.repository.CustomerRepository;



@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceImplTest
{
	
	@InjectMocks
	CustomerServiceImpl customerServiceImpl;
	
	@Mock
	CustomerRepository customerRepository;
	
	CustomerRequestModel customerRequestModel;
	
	Customer customer;
	
	Transaction tran=new Transaction();
	List<Transaction> tranlist=new ArrayList<Transaction>();
	
	@Before
	public void setup()
	
	{
		tranlist.add(tran);
		customer=new Customer(1l,"abc@gmail.com", "mane", "ram", LocalDate.now(), "9552524243", "COOPP1212", "male", tranlist);
		
		customerRequestModel=new CustomerRequestModel("abc@gmail.com", "mane", "ram", LocalDate.now(), "9552524243", "COOPP1212", "male");
		
	}
	
	
	@Test(expected = ApplicationException.class)
	  public void testAddCustomerwithemailvalidation() throws ApplicationException {
		  Mockito.when(customerRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(customer));
		  Customer reponsemodel=customerServiceImpl.addCustomer(customerRequestModel); 
	  }
	
	
	 @Test
	  public void testAddCustomerwithsucess() throws ApplicationException {
		 customer.setEmail("pqr@gmail.com");
		  Mockito.when(customerRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(customer));
		  Mockito.when(customerRepository.save(Mockito.anyObject())).thenReturn(customer);
		  Customer responsemodel=customerServiceImpl.addCustomer(customerRequestModel); 
		  assertNotNull(responsemodel);
		  
		  assertEquals("pqr@gmail.com", responsemodel.getEmail());
		 
	  }
	 
	
	/*
	 * @Test(expected = ApplicationException.class) public void testCustomer() {
	 * Mockito.when(customerRepository.get)).thenReturn(Optional.of(customer));
	 * Customer reponsemodel=customerServiceImpl.getCustomer(1l); }
	 */
	
}
