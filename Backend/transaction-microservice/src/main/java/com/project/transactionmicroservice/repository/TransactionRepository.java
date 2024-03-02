package com.project.transactionmicroservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.transactionmicroservice.entity.FinancialTransactions;

@Repository
public interface TransactionRepository extends JpaRepository<FinancialTransactions, Integer> {
	
	public List<FinancialTransactions>findAllByCustomerId(int customerId);
	public List<FinancialTransactions>findAllByAccountId(int accountId);
}
