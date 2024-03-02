package com.project.Account.Microservice.service;

import java.util.List;

import com.project.Account.Microservice.entity.Account;
import com.project.Account.Microservice.entity.AccountCreationStatus;
import com.project.Account.Microservice.model.TransactionStatus;
import com.project.Account.Microservice.model.TransactionsHistory;
import com.project.Account.Microservice.model.TransactionsHistoryList;

public interface AccountService {
	
	public AccountCreationStatus createAccount(String token,String username,int customerId);
	public List<Account>getCustomerAccounts(String token,String username,int customerId);
	public Account getAccountsByAccountId(String token,String username,int accountId);
	public TransactionStatus deposit(String token,String username,int accountid,Double amount);
	public TransactionStatus withdraw(String token,String username,int accountId,Double amount);
	public TransactionsHistory[] getAccountStatement(String token,String username,int customerId);
	public TransactionStatus transfer(String token,String username,int sourceAccountId,int destinationAccountId,Double amount);
}
