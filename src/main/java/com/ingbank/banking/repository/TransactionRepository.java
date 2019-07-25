package com.ingbank.banking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ingbank.banking.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>
{
	@Query(value="select * from transaction where customer_id=:customerId order by transaction_date desc limit 1", nativeQuery = true)
	public Optional<Transaction> findLastTransaction(Long customerId);

	public Optional<List<Transaction>> findAllByCustomerId(Long userId, Pageable pageable);
		


}
