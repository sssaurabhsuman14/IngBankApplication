package com.ingbank.banking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ingbank.banking.entity.Customer;
import com.ingbank.banking.model.CustomerRequestModel;
import com.ingbank.banking.model.ResponseData;
import com.ingbank.banking.service.CustomerService;


@RestController
@RequestMapping("/register")
public class CustomerContoller 
{
  @Autowired
	CustomerService customerService;
	
	@PostMapping("/")
	public ResponseEntity<ResponseData> createCustomer(@RequestBody CustomerRequestModel customerRequestModel)
	{
		
		Customer customer=customerService.addCustomer(customerRequestModel);
		
		ResponseData response = new ResponseData("Hurrayy!!!, "+customer.getFirstName()+" Welcome to Bank", HttpStatus.OK, customer.getUserId());
		  return new ResponseEntity<>(response, HttpStatus.OK);	
		
		
	}
	
	
	
}
