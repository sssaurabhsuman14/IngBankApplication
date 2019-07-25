package com.ingbank.banking.service;

import org.springframework.stereotype.Service;

import com.ingbank.banking.model.TransactionRequestModel;

@Service
public interface TransactionService {

	void doTransaction(TransactionRequestModel transactionRequest);

}
