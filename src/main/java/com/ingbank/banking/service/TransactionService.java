package com.ingbank.banking.service;

import java.sql.SQLDataException;

import org.springframework.stereotype.Service;

import com.ingbank.banking.entity.Transaction;
import com.ingbank.banking.exception.ApplicationException;
import com.ingbank.banking.model.TransactionRequestModel;


@Service
public interface TransactionService {

	Transaction doTransaction(TransactionRequestModel transactionRequest)throws ApplicationException, SQLDataException;

	//public Map<String, Map<String, List<StatementModel>>> getYearlyStatement(Long customerId, String month);
}
