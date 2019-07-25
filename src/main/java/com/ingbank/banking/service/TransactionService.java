package com.ingbank.banking.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ingbank.banking.model.StatementModel;


@Service
public interface TransactionService {

	//Transaction doTransaction(TransactionRequestModel transactionRequest)throws ApplicationException, SQLDataException;

	public Map<String, List<StatementModel>> getYearlyStatement(Long customerId, String year);
	
	//public List<TransactionHistoryDto> getTransactions(Customer customerId);
	
	public Map<String, Map<String, List<StatementModel>>> getYearly(Long customerId, String year);

}
