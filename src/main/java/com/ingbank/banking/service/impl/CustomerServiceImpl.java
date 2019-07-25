package com.ingbank.banking.service.impl;

<<<<<<< Updated upstream
import com.ingbank.banking.entity.Customer;
import com.ingbank.banking.service.CustomerService;

public class CustomerServiceImpl implements CustomerService 
{

	@Override
	public Customer getCustomer(Long customerId) {
		
		return null;
	}
=======
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
>>>>>>> Stashed changes

import com.ingbank.banking.entity.Customer;
import com.ingbank.banking.model.CustomerRequestModel;
import com.ingbank.banking.repository.CustomerRepository;
import com.ingbank.banking.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService
{

	@Autowired
	CustomerRepository customerRepository;
	
	@Override
	public Customer addCustomer(CustomerRequestModel customerRequestModel) {
		
		
		Customer customerEntityInput=new Customer();
		
		BeanUtils.copyProperties(customerRequestModel, customerEntityInput);
		
		Customer customerEntityoutput=customerRepository.save(customerEntityInput);
		
		
		return  customerEntityoutput;
	}

	
	
}
