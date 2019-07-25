package com.ingbank.banking.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ingbank.banking.model.ResponseData;
import com.ingbank.banking.model.StatementModel;
import com.ingbank.banking.service.TransactionService;

@RestController
@RequestMapping("/transaction")
public class TransactionController 
{
	
	@Autowired
	TransactionService transactionService;
	
	/*
	 * @PostMapping("/") public ResponseEntity<ResponseData>
	 * doTransaction(@RequestBody TransactionRequestModel transactionRequest) throws
	 * SQLDataException, ApplicationException {
	 * 
	 * Transaction doTransaction =
	 * transactionService.doTransaction(transactionRequest); String data =
	 * doTransaction.getTransactionId()+"\n"+doTransaction.getTransactionDescription
	 * ()+"\n"+doTransaction.getBalance(); ResponseData response = new
	 * ResponseData("Hi"+doTransaction.getCustomer().getFirstName()
	 * +"your transaction done successfully", HttpStatus.OK, data ); return new
	 * ResponseEntity<>(response, HttpStatus.OK); }
	 */
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@GetMapping("/viewMonthlyStatement/{customerId}/{year}")
	public ResponseEntity<ResponseData> viewMonthlyStatement(@PathVariable(value = "customerId") Long customerId, @PathVariable(value = "year") String year){
		Map<String, Map<String, List<StatementModel>>> statementMap = null;
		
		 statementMap = transactionService.getYearly(customerId, year);
		ResponseData response = new ResponseData("\"Your monthwise account statement for the year of "+year+": " , HttpStatus.OK, statementMap);

		return new ResponseEntity<ResponseData>(response ,HttpStatus.OK);
	}
	

}
