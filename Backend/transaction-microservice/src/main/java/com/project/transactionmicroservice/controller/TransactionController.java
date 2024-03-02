package com.project.transactionmicroservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.transactionmicroservice.TransactionMicroserviceApplication;
import com.project.transactionmicroservice.model.TransactionStatus;
import com.project.transactionmicroservice.model.TransactionsHistory;
import com.project.transactionmicroservice.service.TransactionService;

@RestController
@CrossOrigin
@RequestMapping("/transaction")
public class TransactionController {

	@Autowired
	private TransactionService service;

	@PostMapping("/deposit/{accountId}/{amount}")
	public ResponseEntity<TransactionStatus> deposit(@RequestHeader("Authorization") String token,
			@RequestHeader("Username") String username, @PathVariable int accountId, @PathVariable Double amount) {

		TransactionStatus status = service.deposit(token, username, accountId, amount);
		return new ResponseEntity<>(status, HttpStatus.CREATED);
	}

	@GetMapping("/getAllTransactions/{customerId}")
	public ResponseEntity<List<TransactionsHistory>> getAllTransactions(@RequestHeader("Authorization") String token,
			@RequestHeader("Username") String username, @PathVariable int customerId) {
		List<TransactionsHistory> transactions = service.getAllTransactions(token, username, customerId);
		return new ResponseEntity<>(transactions, HttpStatus.ACCEPTED);
	}

	@PostMapping("/withdraw/{accountId}/{amount}")
	public ResponseEntity<TransactionStatus> withdraw(@RequestHeader("Authorization") String token,
			@RequestHeader("Username") String username, @PathVariable int accountId, @PathVariable Double amount) {
		TransactionStatus status = service.withdraw(token, username, accountId, amount);
		return new ResponseEntity<>(status, HttpStatus.CREATED);
	}

	@PostMapping("/transfer/{sourceAccountId}/{destinationAccountId}/{amount}")
	public ResponseEntity<TransactionStatus> withdraw(@RequestHeader("Authorization") String token,
			@RequestHeader("Username") String username, @PathVariable int sourceAccountId,
			@PathVariable int destinationAccountId, @PathVariable double amount) {
		TransactionStatus status = service.transfer(token, username, sourceAccountId, sourceAccountId, amount);
		return new ResponseEntity<>(status, HttpStatus.CREATED);
	}

	@GetMapping("/getAllTransactions/{fromDate}/{toDate}/{accountId}")
	public ResponseEntity<List<TransactionsHistory>> getAllTransactionsByDate(
			@RequestHeader("Authorization") String token, @RequestHeader("Username") String username,
			@PathVariable String fromDate, @PathVariable String toDate, @PathVariable int accountId) {
		List<TransactionsHistory> transactions = service.getTransactionsWithinDate(token, username, fromDate, toDate,
				accountId);
		return new ResponseEntity<>(transactions, HttpStatus.ACCEPTED);
	}

}
