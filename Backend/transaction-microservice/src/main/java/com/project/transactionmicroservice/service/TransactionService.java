package com.project.transactionmicroservice.service;

import java.util.List;

import com.project.transactionmicroservice.model.TransactionStatus;
import com.project.transactionmicroservice.model.TransactionsHistory;

public interface TransactionService {
		
	public TransactionStatus deposit(String token,String username,int accountId,Double amount);
	public List<TransactionsHistory> getAllTransactions(String token,String username,int customerId);
	public TransactionStatus withdraw(String token,String username,int accountId,Double amount);
	public TransactionStatus transfer(String token,String username,int source_accountId,int destination_accountId,double amount);
	public List<TransactionsHistory> getTransactionsWithinDate(String token,String username,String fromDate,String todate,int accountId);
}
