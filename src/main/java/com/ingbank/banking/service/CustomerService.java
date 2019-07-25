package com.ingbank.banking.service;

import org.springframework.stereotype.Service;

import com.ingbank.banking.entity.Customer;
<<<<<<< Updated upstream
=======
import com.ingbank.banking.model.CustomerRequestModel;
>>>>>>> Stashed changes

@Service
public interface CustomerService
{

	Customer getCustomer(Long customerId);
	
	 public Customer addCustomer(CustomerRequestModel customerRequestModel);
	 
	
}