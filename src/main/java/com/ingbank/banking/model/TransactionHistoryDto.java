package com.ingbank.banking.model;

import java.time.LocalDateTime;

import com.ingbank.banking.entity.Customer;

import lombok.Data;

@Data
public class TransactionHistoryDto {
	
	private Customer customer;
	private String transactionDescription;
	private double transactionAmount;
	private String transactionType;
	private LocalDateTime transactionDateTime;
	private Long transactionId;
	private String status;

		

} 
 

