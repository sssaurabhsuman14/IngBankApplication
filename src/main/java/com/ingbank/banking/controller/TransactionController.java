package com.ingbank.banking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ingbank.banking.model.ResponseData;
import com.ingbank.banking.model.TransactionRequestModel;
import com.ingbank.banking.service.TransactionService;

@RestController
@RequestMapping("/transaction")
public class TransactionController 
{
	
	@Autowired
	TransactionService transactionService;
	
	@PostMapping("/")
	public ResponseEntity<ResponseData> doTransaction(@RequestBody TransactionRequestModel transactionRequest)
	{
		transactionService.doTransaction(transactionRequest);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	

}
