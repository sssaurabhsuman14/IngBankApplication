package com.ingbank.banking.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ingbank.banking.entity.Customer;
import com.ingbank.banking.model.TransactionRequestModel;
import com.ingbank.banking.service.CustomerService;
import com.ingbank.banking.service.TransactionService;
import java.util.List;
import java.util.Map;

import com.ingbank.banking.model.StatementModel;
@Service
public class TransactionServiceImpl implements TransactionService
{
	@Autowired
	CustomerService CustomerService;

	@Override
	public void doTransaction(TransactionRequestModel transactionRequest) 
	{
		Customer customer = CustomerService.getCustomer(transactionRequest.getCustomerId());
	}		

/*	public Map<String, Map<String, List<StatementModel>>> getYearlyStatement(Long customerId, String month){
		Customer customer = CustomerService.getCustomer(customerId);
		
		return statementMap;
		
	}*/

}
