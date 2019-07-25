package com.ingbank.banking.service.impl;

import java.sql.SQLDataException;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ingbank.banking.entity.Customer;
import com.ingbank.banking.entity.Transaction;
import com.ingbank.banking.exception.ApplicationException;
import com.ingbank.banking.model.TransactionRequestModel;
import com.ingbank.banking.repository.TransactionRepository;
import com.ingbank.banking.service.CustomerService;
import com.ingbank.banking.service.TransactionService;
@Service
public class TransactionServiceImpl implements TransactionService {
	@Autowired
	CustomerService customerService;

	@Autowired
	TransactionRepository transactionRepository;

	
		

/*	public Map<String, Map<String, List<StatementModel>>> getYearlyStatement(Long customerId, String month){
		Customer customer = CustomerService.getCustomer(customerId);
		
		return statementMap;
		
	}*/

	@Override
	public Transaction doTransaction(TransactionRequestModel transactionRequest)
			throws ApplicationException, SQLDataException {
		Transaction fetchLastTransaction = fetchLastTransaction(transactionRequest.getCustomerId());
		Transaction currentTransaction = new Transaction();

		if(!ObjectUtils.isEmpty(fetchLastTransaction)) 
		{
			if (transactionRequest.getTransactionType().equalsIgnoreCase("RECEIVE PAYMENT")) 
			{
				receivePayment(transactionRequest, fetchLastTransaction);
			} 
			else 
			{
				currentTransaction = makePayment(transactionRequest, fetchLastTransaction);
			}
		}
		else
		{
			if (transactionRequest.getTransactionType().equalsIgnoreCase("RECEIVE PAYMENT")) 
			{
				currentTransaction = receivePayment(transactionRequest, fetchLastTransaction);
			}
			else
			{
				throw new ApplicationException(
						"Hi," + fetchLastTransaction.getCustomer().getFirstName() + " you dont have sufficient balance");
			}
		}
		return currentTransaction;
	}

	private Transaction makePayment(TransactionRequestModel transactionRequest, Transaction fetchLastTransaction)
			throws ApplicationException {
		if (fetchLastTransaction.getBalance() < transactionRequest.getTransactionAmount())
			throw new ApplicationException(
					"Hi," + fetchLastTransaction.getCustomer().getFirstName() + " you dont have sufficient balance");
		else {

			Transaction newTransaction = new Transaction();
			newTransaction.setTransactionType(transactionRequest.getTransactionType());
			newTransaction.setTransactionDescription(transactionRequest.getTransactionDescription());
			newTransaction.setCustomer(fetchLastTransaction.getCustomer());
			newTransaction.setStatus("PENDING");
			newTransaction.setTransactionDateTime(LocalDateTime.now());
			newTransaction.setTransactionAmount(transactionRequest.getTransactionAmount());
			newTransaction.setBalance(fetchLastTransaction.getBalance() - transactionRequest.getTransactionAmount());

			return transactionRepository.save(newTransaction);
		}

	}

	private Transaction receivePayment(TransactionRequestModel transactionRequest, Transaction fetchLastTransaction) {
		Transaction newTransaction = new Transaction();
		newTransaction.setTransactionType(transactionRequest.getTransactionType());
		newTransaction.setTransactionDescription(transactionRequest.getTransactionDescription());
		newTransaction.setCustomer(fetchLastTransaction.getCustomer());
		newTransaction.setStatus("PENDING");
		newTransaction.setTransactionDateTime(LocalDateTime.now());
		newTransaction.setTransactionAmount(transactionRequest.getTransactionAmount());
		newTransaction.setBalance(transactionRequest.getTransactionAmount() + fetchLastTransaction.getBalance());

		return transactionRepository.save(newTransaction);

	}

	private Transaction fetchLastTransaction(Long customerId) throws ApplicationException {
		Transaction transaction = new Transaction();
		Customer customer = customerService.getCustomer(customerId);
		Sort sortByTransactionDateTime = Sort.by(Sort.Direction.ASC, "transactionDateTime");
		Pageable page = PageRequest.of(0, 1, sortByTransactionDateTime);
		Optional<Transaction> lastTransactionOptional = transactionRepository.findByCustomer(customer, page);
		boolean isOptionalPresent = lastTransactionOptional.isPresent();
		if (isOptionalPresent) {
			transaction = lastTransactionOptional.get();
		}
		return transaction;
	}
	
	public Transaction getTransactionById(Long id) {
		Optional<Transaction> transaction = transactionRepository.findById(id);
		if(transaction.isPresent()) {
			return transaction.get();
		}
		return transaction.get();
	}

}
