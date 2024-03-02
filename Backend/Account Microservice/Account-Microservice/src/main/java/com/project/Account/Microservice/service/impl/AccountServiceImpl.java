package com.project.Account.Microservice.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.project.Account.Microservice.entity.Account;
import com.project.Account.Microservice.entity.AccountCreationStatus;
import com.project.Account.Microservice.model.TransactionStatus;
import com.project.Account.Microservice.model.TransactionsHistory;
import com.project.Account.Microservice.model.TransactionsHistoryList;
import com.project.Account.Microservice.repository.AccountRepository;
import com.project.Account.Microservice.service.AccountService;
import com.project.Account.Microservice.exception.AccountNotPresentException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public AccountCreationStatus createAccount(String token,String username, int customerId) {
		Account acc1 = new Account();
		acc1.setAccountType("savings");
		acc1.setCustomerId(customerId);

		Account acc2 = new Account();
		acc2.setAccountType("current");

		acc2.setCustomerId(customerId);
		Account savedAccount1 = accountRepository.save(acc1);
		Account savedAccount2 = accountRepository.save(acc2);
		log.info("Account Saved Succcessgully");
		String msg = "Account Saved Successfully";
		AccountCreationStatus status = new AccountCreationStatus();
		status.setMessage(msg);
		status.setAccountId(List.of(savedAccount1.getAccountId(), savedAccount2.getAccountId()));
		return status;
	}

	@Override
	public List<Account> getCustomerAccounts(String token,String username, int customerId) {
		log.info("Fetching account with the followwing customer id {}", customerId);
		return accountRepository.getAccountByCustomerId(customerId);
	}

	@Override
	public Account getAccountsByAccountId(String token,String username, int accountId) {
		log.info("Fetching account with the following account id {} ", accountId);
		Optional<Account> account = accountRepository.getAccountByAccountId(accountId);
		if (account.isPresent()) {
			return account.get();
		}
		throw new AccountNotPresentException("Account with the id: " + accountId + " is not present");
	}

	@Override
	public TransactionStatus deposit(String token,String username, int accountId, Double amount) {

//		HttpEntity<String> request = new HttpEntity<String>("Json");
		HttpHeaders headers = new HttpHeaders();

		// set `Content-Type` and `Accept` headers
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		// example of custom header
		headers.set("Authorization", token);
		headers.set("Username", username);

		// build the request
		HttpEntity request = new HttpEntity(headers);

		String url = "http://localhost:9003/transaction/deposit/" + accountId + "/" + amount;
		ResponseEntity<TransactionStatus> response = restTemplate.exchange(url, HttpMethod.POST, request,
				TransactionStatus.class

		);
		TransactionStatus status = response.getBody();

		TransactionStatus transactionStatus = new TransactionStatus();
		if (status.getStatus().equals("SUCCESSFULL")) {
			Optional<Account> accountOpt = accountRepository.getAccountByAccountId(accountId);
			if (!accountOpt.isPresent()) {
				throw new AccountNotPresentException("Account with the id: " + accountId + " is not present");
			}
			Account account = accountOpt.get();
			account.setBalance(account.getBalance() + amount);

			accountRepository.save(account);
			status.setStatus("SUCCESSFULL");
		} else {
			status.setStatus("UNSUCCESSFULL");
		}
		return status;
	}

	@Override
	public TransactionStatus withdraw(String token,String username ,int accountId, Double amount) {
//		HttpEntity<String> request = new HttpEntity<String>("Json");
		HttpHeaders headers = new HttpHeaders();

		// set `Content-Type` and `Accept` headers
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		// example of custom header
		headers.set("Authorization", token);
		headers.set("Username", username);

		// build the request
		HttpEntity request = new HttpEntity(headers);

		String url = "http://localhost:9003/transaction/withdraw/" + accountId + "/" + amount;
		ResponseEntity<TransactionStatus> response = restTemplate.exchange(url, HttpMethod.POST, request,
				TransactionStatus.class

		);
		TransactionStatus status = response.getBody();

		TransactionStatus transactionStatus = new TransactionStatus();
		if (status.getStatus().equals("SUCCESSFULL")) {
			Optional<Account> accountOpt = accountRepository.getAccountByAccountId(accountId);
			if (!accountOpt.isPresent()) {
				throw new AccountNotPresentException("Account with the id: " + accountId + " is not present");
			}
			Account account = accountOpt.get();
			account.setBalance(account.getBalance() - amount);

			accountRepository.save(account);
			status.setStatus("SUCCESSFULL");
		} else {
			status.setStatus("UNSUCCESSFULL");
		}
		return status;
	}

	@Override
	public TransactionsHistory[] getAccountStatement(String token, String username,int customerId) {
		HttpHeaders headers = new HttpHeaders();

		// set `Content-Type` and `Accept` headers
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		// example of custom header
		headers.set("Authorization", token);
		headers.set("Username", username);

		// build the request
		HttpEntity request = new HttpEntity(headers);

		String url = "http://localhost:9003/transaction/getAllTransactions/" + customerId;
		ResponseEntity<TransactionsHistory[]> response = restTemplate.exchange(url, HttpMethod.GET, request,
				TransactionsHistory[].class

		);

		return response.getBody();
	}

	@Override
	public TransactionStatus transfer(String token,String username, int sourceAccountId, int destinationAccountId, Double amount) {
		HttpHeaders headers = new HttpHeaders();

		// set `Content-Type` and `Accept` headers
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		// example of custom header
		headers.set("Authorization", token);
		headers.set("Username", username);

		// build the request
		HttpEntity request = new HttpEntity(headers);

		String url = "http://localhost:9003/transaction/transfer/" + sourceAccountId + "/" + destinationAccountId + "/"
				+ amount;
		ResponseEntity<TransactionStatus> response = restTemplate.exchange(url, HttpMethod.POST, request,
				TransactionStatus.class

		);
		TransactionStatus status = response.getBody();

		TransactionStatus transactionStatus = new TransactionStatus();
		if (status.getStatus().equals("SUCCESSFULL")) {
			TransactionStatus withdrawStatus = withdraw(token,username, sourceAccountId, amount);
			TransactionStatus depositStatus = deposit(token,username, destinationAccountId, amount);

			if (withdrawStatus.getStatus().equals("SUCCESSFULL") && depositStatus.getStatus().equals("SUCCESSFULL")) {
				transactionStatus.setStatus("SUCCESSFULL");
			} else {
				transactionStatus.setStatus("UNSUCCESSFULL");
			}

		} else {
			transactionStatus.setStatus("UNSUCCESSFULL");
		}

		return transactionStatus;
	}

}
