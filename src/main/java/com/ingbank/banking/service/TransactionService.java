package com.ingbank.banking.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ingbank.banking.model.StatementModel;
import com.ingbank.banking.model.TransactionRequestModel;


@Service
public interface TransactionService {

	void doTransaction(TransactionRequestModel transactionRequest);

	//public Map<String, Map<String, List<StatementModel>>> getYearlyStatement(Long customerId, String month);
}
