package com.ingbank.banking.service;

import org.springframework.stereotype.Service;

import com.ingbank.banking.entity.Customer;

@Service
public interface CustomerService
{

	Customer getCustomer(Long customerId);
	
	
}