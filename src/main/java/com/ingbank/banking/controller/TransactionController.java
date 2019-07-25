package com.ingbank.banking.controller;

import java.sql.SQLDataException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ingbank.banking.entity.Customer;
import com.ingbank.banking.entity.Transaction;
import com.ingbank.banking.exception.ApplicationException;
import com.ingbank.banking.model.ResponseData;
import com.ingbank.banking.model.StatementModel;
import com.ingbank.banking.model.TransactionRequestModel;
import com.ingbank.banking.service.CustomerService;
import com.ingbank.banking.service.TransactionService;
import com.ingbank.banking.validation.ApplicationValidation;

@RestController
@RequestMapping("/transaction")
@CrossOrigin
public class TransactionController {

	@Autowired
	CustomerService customerService;

	@Autowired
	ApplicationValidation applicationValidation;

	private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

	@Autowired
	TransactionService transactionService;

	@GetMapping("/viewMonthlyStatement/{customerId}/{year}")
	public ResponseEntity<ResponseData> viewMonthlyStatement(@PathVariable(value = "customerId") Long customerId,
			@PathVariable(value = "year") String year) {
		Map<String, Map<String, List<StatementModel>>> statementMap = null;

		statementMap = transactionService.getYearly(customerId, year);
		ResponseData response = new ResponseData("\"Your monthwise account statement for the year of " + year + ": ",
				HttpStatus.OK, statementMap);

		return new ResponseEntity<ResponseData>(response, HttpStatus.OK);
	}

	@PostMapping("/")
	public ResponseEntity<ResponseData> doTransaction(@RequestBody TransactionRequestModel transactionRequest)
			throws SQLDataException, ApplicationException {
		logger.debug("CONTROLLER : TransactionController METHOD : doTransaction() enter");

		applicationValidation.validateTransactionRequest(transactionRequest);

		Transaction doTransaction = transactionService.doTransaction(transactionRequest);
		Customer customer = customerService.getCustomer(transactionRequest.getCustomerId());

		String data = " /Transaction Id : " + doTransaction.getTransactionId() + " /Description : "
				+ doTransaction.getTransactionDescription() + " /Balance : " + doTransaction.getBalance() + "/";
		ResponseData response = new ResponseData(
				"Hi, " + customer.getFirstName() + " your transaction done successfully", HttpStatus.OK, data);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/transactionHistory")
	public ResponseEntity<ResponseData> viewTransactionHistory(@RequestParam("customerId") Long userId)
			throws ApplicationException {

		if (userId == 0) {
			ResponseData response = new ResponseData("Please provide customer id to view transaction history. ",
					HttpStatus.BAD_REQUEST, null);
			return new ResponseEntity<ResponseData>(response, HttpStatus.OK);
		} else {
			if (transactionService.doCheckCustomerId(userId)) {
				Optional<List<Transaction>> transactionList = transactionService.viewTransactionHistory(userId);
				if (transactionList.isPresent()) {
					ResponseData response = new ResponseData(
							"Please find below transaction history for customer id: " + userId, HttpStatus.OK,
							transactionList);
					return new ResponseEntity<ResponseData>(response, HttpStatus.OK);
				} else {
					ResponseData response = new ResponseData("No transaction history for customer id: " + userId,
							HttpStatus.BAD_REQUEST, null);
					return new ResponseEntity<ResponseData>(response, HttpStatus.OK);
				}
			} else {
				ResponseData response = new ResponseData(
						"Please provide valid customer id to view transaction history. ", HttpStatus.BAD_REQUEST, null);
				return new ResponseEntity<ResponseData>(response, HttpStatus.OK);

			}
		}

	}
}
