package com.project.transactionmicroservice.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.project.transactionmicroservice.entity.FinancialTransactions;
import com.project.transactionmicroservice.entity.RefPaymentMethods;
import com.project.transactionmicroservice.entity.RefTransactionStatus;
import com.project.transactionmicroservice.entity.RefTransactionTypes;
import com.project.transactionmicroservice.model.Account;
import com.project.transactionmicroservice.model.RuleStatus;
import com.project.transactionmicroservice.model.TransactionStatus;
import com.project.transactionmicroservice.model.TransactionsHistory;
import com.project.transactionmicroservice.repository.TransactionRepository;
import com.project.transactionmicroservice.service.TransactionService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepository repository;
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public TransactionStatus deposit(String token,String username, int accountId, Double amount) {

		log.info("Saving {} amount into accountId:{}", amount, accountId);

		// Payment Method
		RefPaymentMethods method = new RefPaymentMethods();
		method.setPaymentMethodName("MASTERCARD");
		// Transaction Type
		RefTransactionTypes transactionType = new RefTransactionTypes();
		transactionType.setTransactionTypeDescription("PAYMENT DEPOSIT");
		// Ref Transaction Status
		RefTransactionStatus status = new RefTransactionStatus();
		status.setTransactionStatusDescription("SUCCESSFULL");
		FinancialTransactions transaction = new FinancialTransactions();
		transaction.setAmountOfTransaction(amount);
		transaction.setRefTransactionStatus(status);
		transaction.setRefTransactionTypes(transactionType);
		transaction.setRefPaymentMethods(method);
		transaction.setAccountId(accountId);
		// create headers
		HttpHeaders headers = new HttpHeaders();

		// set `Content-Type` and `Accept` headers
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		// example of custom header
		headers.set("Authorization", token);
		headers.set("Username", username);

		// build the request
		HttpEntity request = new HttpEntity(headers);
		String url = "http://localhost:9001/account/getAccount/" + accountId;
		// make an HTTP GET request with headers
		ResponseEntity<Account> accountResponse = restTemplate.exchange(url, HttpMethod.GET, request, Account.class

		);
		Account account = accountResponse.getBody();
//		// Getting Customer Information

		transaction.setCustomerId(account.getCustomerId());
		repository.save(transaction);
		TransactionStatus statusFinal = new TransactionStatus("SUCCESSFULL");
		return statusFinal;
	}

	@Override
	public List<TransactionsHistory> getAllTransactions(String token,String username, int customerId) {
		List<FinancialTransactions> transactions = repository.findAllByCustomerId(customerId);

		List<TransactionsHistory> result = transactions.stream()
				.map(transaction -> mapTransactionToTransactionHistory(transaction)).collect(Collectors.toList());
		return result;
	}

	private TransactionsHistory mapTransactionToTransactionHistory(FinancialTransactions transaction) {

		TransactionsHistory transactionHistory = new TransactionsHistory();
		transactionHistory.setAmountOfTransaction(transaction.getAmountOfTransaction());
		transactionHistory.setTransactionDate(transaction.getDateofTransaction());
		transactionHistory.setTransactionMethod(transaction.getRefPaymentMethods().getPaymentMethodName());
		transactionHistory.setTransactionId(transaction.getTransactionId());
		transactionHistory
				.setTrsansactionStatus(transaction.getRefTransactionStatus().getTransactionStatusDescription());
//		transactionHistory.setCounterparties(transaction.getCounterparties().getCounterpartyName());

		transactionHistory.setAccountId(transaction.getAccountId());
		transactionHistory.setTransactionType(transaction.getRefTransactionTypes().getTransactionTypeDescription());

		return transactionHistory;

	}

	@Override
	public TransactionStatus withdraw(String token,String username, int accountId, Double amount) {
		// Payment Method
		RefPaymentMethods method = new RefPaymentMethods();
		method.setPaymentMethodName("MASTERCARD");
		// Transaction Type
		RefTransactionTypes transactionType = new RefTransactionTypes();
		transactionType.setTransactionTypeDescription("PAYMENT WITHDRAW");

		HttpHeaders headers = new HttpHeaders();

		// set `Content-Type` and `Accept` headers
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		// example of custom header
		headers.set("Authorization", token);
		headers.set("Username", username);

		// build the request
		HttpEntity request = new HttpEntity(headers);
		String url = "http://localhost:9002/rules/evaluateMinBal/" + amount + "/" + accountId;
		ResponseEntity<RuleStatus> response = restTemplate.exchange(url, HttpMethod.GET, request, RuleStatus.class

		);

		RuleStatus status = response.getBody();

		System.out.println(status.getStatus());
		TransactionStatus transactionStatus = new TransactionStatus();
		RefTransactionStatus statusTransaction = new RefTransactionStatus();
		statusTransaction.setTransactionStatusDescription("SUCCESSFULL");
		if (status.getStatus().equals("ALLOWED")) {
			FinancialTransactions transaction = new FinancialTransactions();
			transaction.setAmountOfTransaction(amount);
			transaction.setRefTransactionStatus(statusTransaction);
			transaction.setRefTransactionTypes(transactionType);
			transaction.setRefPaymentMethods(method);
			transaction.setAccountId(accountId);
			// create headers
			headers = new HttpHeaders();

			// set `Content-Type` and `Accept` headers
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

			// example of custom header
			headers.set("Authorization", token);
			headers.set("Username", username);

			// build the request
			request = new HttpEntity(headers);
			url = "http://localhost:9001/account/getAccount/" + accountId;
			// make an HTTP GET request with headers
			ResponseEntity<Account> accountResponse = restTemplate.exchange(url, HttpMethod.GET, request, Account.class

			);
			Account account = accountResponse.getBody();
			// Getting Customer Information

			transaction.setCustomerId(account.getCustomerId());
			repository.save(transaction);
			transactionStatus.setStatus("SUCCESSFULL");
		} else if (status.getStatus().equals("DENIED")) {
			transactionStatus.setStatus("UNSUCCESSFULL");
		} else {
			transactionStatus.setStatus("SUCCESSFULL");
		}
		return transactionStatus;
	}

	@Override
	public TransactionStatus transfer(String token,String username, int source_accountId, int destination_accountId, double amount) {
		TransactionStatus statusRecieved = withdraw(token, username, source_accountId, amount);
		TransactionStatus result = new TransactionStatus();
		if (statusRecieved.getStatus().equals("SUCCESSFULL")) {
			result.setStatus("SUCCESSFULL");
		} else {
			result.setStatus("UNSUCCESSFULL");
		}
		return result;
	}

	@Override
	public List<TransactionsHistory> getTransactionsWithinDate(String token, String username,String fromDate, String todate,
			int accountId) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = df.parse(fromDate);
			endDate = df.parse(todate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		List<FinancialTransactions> transactions = repository.findAllByAccountId(accountId);
		List<TransactionsHistory> result = transactions.stream()
				.map(transaction -> mapTransactionToTransactionHistory(transaction)).collect(Collectors.toList());
		List<TransactionsHistory> finalResult = new ArrayList<TransactionsHistory>();
		for (TransactionsHistory tran : result) {
			System.out.println(checkDate(startDate, endDate, tran.getTransactionDate()));
			if (checkDate(startDate, endDate, tran.getTransactionDate())) {
				finalResult.add(tran);
			}
		}
		return finalResult;
	}

	private boolean checkDate(Date fromDate, Date toDate, Date current) {
		if (current.compareTo(toDate) > 0) {
			return false;
		} else if (current.compareTo(fromDate) < 0) {
			return false;
		}
		return true;
	}

}
