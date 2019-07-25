package com.ingbank.banking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
<<<<<<< Updated upstream
=======

import com.ingbank.banking.entity.Transaction;
import com.ingbank.banking.repository.TransactionRepository;
>>>>>>> Stashed changes

@SpringBootApplication
public class BankingApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(BankingApplication.class, args);
		BankingApplication ba = new BankingApplication();
	}

}
