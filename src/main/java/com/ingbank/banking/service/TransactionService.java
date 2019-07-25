package com.ingbank.banking.service;

import java.sql.SQLDataException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ingbank.banking.entity.Transaction;
import com.ingbank.banking.exception.ApplicationException;
import com.ingbank.banking.model.StatementModel;
import com.ingbank.banking.model.TransactionRequestModel;


@Service
public interface TransactionService {


	public Map<String, List<StatementModel>> getYearlyStatement(Long customerId, String year);
	
	
	public Map<String, Map<String, List<StatementModel>>> getYearly(Long customerId, String year);

	
	
	public Transaction getTransactionById(Long id);

	public Transaction doTransaction(TransactionRequestModel transactionRequest) throws ApplicationException, SQLDataException;


	boolean doCheckCustomerId(Long customerId);


	public Optional<List<Transaction>> viewTransactionHistory(Long userId) throws ApplicationException;
}
