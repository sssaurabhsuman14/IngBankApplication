package com.ingbank.banking.service.impl;

import java.sql.SQLDataException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ingbank.banking.entity.Customer;
import com.ingbank.banking.entity.Transaction;
import com.ingbank.banking.exception.ApplicationException;
import com.ingbank.banking.model.StatementModel;
import com.ingbank.banking.model.TransactionRequestModel;
import com.ingbank.banking.repository.CustomerRepository;
import com.ingbank.banking.repository.TransactionRepository;
import com.ingbank.banking.service.CustomerService;
import com.ingbank.banking.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	CustomerService customerService;

	@Autowired
	TransactionService TransactionService;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	TransactionRepository transactionRepository;


	public Map<String, List<StatementModel>> getYearlyStatement(Long customerId, String year){ 

		Map<String, List<StatementModel>> transactionsMap = new HashMap<String, List<StatementModel>>();
		Customer customer;

		List<String> list = monthNameList();

		try {
			customer = customerService.getCustomer(customerId);
			for(String month : list){
				List<StatementModel> statementModelList = new ArrayList<StatementModel>();

				Optional<List<Transaction>> transactionList = viewTransactionHistory(customerId); 
				StatementModel statementModel = new StatementModel();
				statementModel.setTotalIncoming(0.00);
				statementModel.setTotalOutgoing(0.00);
				statementModel.setClosingBalance(0.00);
				statementModel.setCustomerId(customerId);

				for(Transaction transaction : transactionList.get()) {

					if(transaction.getTransactionDateTime().getMonth().name().equalsIgnoreCase(month)) {
						if(transaction.getTransactionType().equalsIgnoreCase("Credit")) {

							statementModel.setTotalIncoming(transaction.getTransactionAmount() + statementModel.getTotalIncoming());

						} else {
							statementModel.setTotalOutgoing(transaction.getTransactionAmount() + statementModel.getTotalOutgoing());
						}
					} 
					statementModel.setClosingBalance(statementModel.getTotalIncoming()-statementModel.getTotalOutgoing());

				}
				if(statementModel.getClosingBalance()>0 && statementModel.getTotalIncoming()>0 && statementModel.getTotalOutgoing()>0) {
					statementModelList.add(statementModel);
				}
				if(statementModelList.size()>0) {
					transactionsMap.put(month ,statementModelList);
				}
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
			return transactionsMap;
		}
		return transactionsMap;
	}
	
			
			@Override
	public Transaction doTransaction(TransactionRequestModel transactionRequest)
			throws ApplicationException, SQLDataException 
	{
		Transaction fetchLastTransaction = fetchLastTransaction(transactionRequest.getCustomerId());
		Customer customer = customerService.getCustomer(transactionRequest.getCustomerId());
		Transaction currentTransaction = new Transaction();
		
		if(fetchLastTransaction.getTransactionId() == null)
		{
			if (transactionRequest.getTransactionType().equalsIgnoreCase("RECEIVE PAYMENT")) 
			{
				currentTransaction = receivePayment(transactionRequest, fetchLastTransaction, customer);
			}
			else
			{
				throw new ApplicationException(
						"Hi," + customer.getFirstName() + " you dont have sufficient balance");
			}
			
		}
		else
		{
			if (transactionRequest.getTransactionType().equalsIgnoreCase("RECEIVE PAYMENT")) 
			{
				currentTransaction = receivePayment(transactionRequest, fetchLastTransaction, customer);
			} 
			else 
			{
				currentTransaction = makePayment(transactionRequest, fetchLastTransaction, customer);
			}
		}
		
		return currentTransaction;
	}

	private Transaction makePayment(TransactionRequestModel transactionRequest, Transaction fetchLastTransaction, Customer customer)
			throws ApplicationException 
	{
		if (fetchLastTransaction.getBalance() < transactionRequest.getTransactionAmount())
			throw new ApplicationException(
					"Hi," + customer.getFirstName() + " you dont have sufficient balance");
		else {

			Transaction newTransaction = new Transaction();
			newTransaction.setTransactionType(transactionRequest.getTransactionType());
			newTransaction.setTransactionDescription(transactionRequest.getTransactionDescription());
			newTransaction.setCustomerId(transactionRequest.getCustomerId());
			newTransaction.setStatus("PENDING");
			newTransaction.setTransactionDateTime(LocalDateTime.now());
			newTransaction.setTransactionAmount(transactionRequest.getTransactionAmount());
			newTransaction.setBalance(fetchLastTransaction.getBalance() - transactionRequest.getTransactionAmount());

			return transactionRepository.save(newTransaction);
		}
	}
		
	public Map<String, Map<String, List<StatementModel>>> getYearly(Long customerId, String year){
		Map<String, Map<String, List<StatementModel>>> yearMap = new HashMap<>();
		Map map = this.getYearlyStatement(customerId, year);
		yearMap.put(year, map);
		return yearMap;
	}

	public List<String> monthNameList(){

		List<String> list = new ArrayList<String>();
		list.add("January");
		list.add("February");
		list.add("March");
		list.add("April");
		list.add("May");
		list.add("June");
		list.add("July");
		list.add("August");
		list.add("September");
		list.add("October");
		list.add("November");
		list.add("December");
		return list;
	}
	private Transaction receivePayment(TransactionRequestModel transactionRequest, Transaction fetchLastTransaction, Customer customer) {
		Transaction newTransaction = new Transaction();
		newTransaction.setTransactionType(transactionRequest.getTransactionType());
		newTransaction.setTransactionDescription(transactionRequest.getTransactionDescription());
		newTransaction.setCustomerId(transactionRequest.getCustomerId());
		newTransaction.setStatus("PENDING");
		newTransaction.setTransactionDateTime(LocalDateTime.now());
		newTransaction.setTransactionAmount(transactionRequest.getTransactionAmount());
		if(fetchLastTransaction.getBalance() == null)
			newTransaction.setBalance(transactionRequest.getTransactionAmount());
		else
			newTransaction.setBalance(transactionRequest.getTransactionAmount() + fetchLastTransaction.getBalance());

		return transactionRepository.save(newTransaction);

	}

	private Transaction fetchLastTransaction(Long customerId) throws ApplicationException 
	{
		Transaction transaction = new Transaction();
		
		Optional<Transaction> lastTransactionOptional = transactionRepository.findLastTransaction(customerId);
		
		boolean isOptionalPresent = lastTransactionOptional.isPresent();
		if (isOptionalPresent) 
		{
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
	
	@Override
	public boolean doCheckCustomerId(Long customerId) {
	 Optional<Customer> customer = customerRepository.findById(customerId);
	 if(customer.isPresent()) {
	  return true;
	 }
	 return false;
	}

	@Override
	public Optional<List<Transaction>> viewTransactionHistory(Long userId) throws ApplicationException {
	 Customer customer = customerService.getCustomer(userId);
	 Pageable pageable = PageRequest.of(0, 10, Sort.by("transactionId").descending());
	 Optional<List<Transaction>>transactionListOptional=transactionRepository.findAllByUserId(customer.getUserId(),pageable);
	 
	    return transactionListOptional;
	 }



}