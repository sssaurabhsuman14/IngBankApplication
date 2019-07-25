package com.ingbank.banking.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.ingbank.banking.entity.Customer;
import com.ingbank.banking.exception.ApplicationException;
import com.ingbank.banking.model.CustomerRequestModel;
import com.ingbank.banking.model.ResponseData;
import com.ingbank.banking.service.CustomerService;

@RunWith(MockitoJUnitRunner.class)
public class CustomerContollerTest
{
	@InjectMocks
	CustomerContoller customerController;
	
	@Mock
	CustomerService customerService;
	
	CustomerRequestModel customerrequestmodel =new CustomerRequestModel();
	
	@Before
	public void setup()
	
	{
		customerrequestmodel.setDateOfBirth(LocalDate.now());
		customerrequestmodel.setEmail("abc.com");
		customerrequestmodel.setFirstName("ram");
		customerrequestmodel.setGender("male");
		customerrequestmodel.setLastName("mane");
		customerrequestmodel.setPanNumber("COOPP1212");
		customerrequestmodel.setPhoneNumber("9552524243");
		
		
		
	}
	
	
	@Test(expected = ApplicationException.class)
	public void createCustomerFailWithEmptyMail() throws ApplicationException 
	{
		ResponseEntity<ResponseData> responseData=customerController.createCustomer(new CustomerRequestModel("", "mane", "ram", LocalDate.now(), "9552524243", "COOPP1212", "male"));
		assertNotNull(responseData);
		
	}
	
	
	@Test(expected = ApplicationException.class)
	public void createCustomerFailwithEmptyPanno() throws ApplicationException 
	{
		ResponseEntity<ResponseData> responseData=customerController.createCustomer(new CustomerRequestModel("ram@gmail.com", "mane", "ram", LocalDate.now(), "9552524243", "", "male"));
		assertNotNull(responseData);
		
	}
	
	@Test
	public void createCustomerWithSucess() throws ApplicationException 
	{
		Mockito.when(customerService.addCustomer(Mockito.anyObject())).thenReturn(new Customer());
	    ResponseEntity<ResponseData>responsedata=customerController.createCustomer(customerrequestmodel);
		assertNotNull(responsedata);	
		assertEquals(200, responsedata.getBody().getResponseStatus().value());
		
	}
	
	
	
	
	
}