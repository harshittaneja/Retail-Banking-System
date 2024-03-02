package com.project.Account.Microservice.controller;

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

import com.project.Account.Microservice.entity.Account;
import com.project.Account.Microservice.entity.AccountCreationStatus;
import com.project.Account.Microservice.exception.TokenNotFoundException;
import com.project.Account.Microservice.model.TransactionStatus;
import com.project.Account.Microservice.model.TransactionsHistory;
import com.project.Account.Microservice.model.TransactionsHistoryList;
import com.project.Account.Microservice.service.AccountService;
import com.project.Account.Microservice.utils.JWTUtil;

@RestController
@CrossOrigin
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private AccountService accountService;
	@Autowired
	private JWTUtil util;

	@PostMapping("/createAccount/{customerId}")
	public ResponseEntity<AccountCreationStatus> createAccount(@RequestHeader("Authorization") String token,
			@RequestHeader("Username") String username,
			@PathVariable int customerId) {
		
		if (token == null || token.length() == 0 || !util.validateToken(token,username)) {

			throw new TokenNotFoundException("Not Authorized");
		}

		AccountCreationStatus status = accountService.createAccount(token,username, customerId);
		return new ResponseEntity<>(status, HttpStatus.CREATED);

	}

	@GetMapping("/getCustomerAccounts/{customerId}")
	public ResponseEntity<List<Account>> getAccountByCustomerid(@RequestHeader("Authorization") String token,
			@RequestHeader("Username") String username,
			@PathVariable int customerId) {
		if (token == null || token.length() == 0 || !util.validateToken(token,username)) {
			throw new TokenNotFoundException("Not Authorized");
		}
		List<Account> accountList = accountService.getCustomerAccounts(token, username,customerId);
		return new ResponseEntity<>(accountList, HttpStatus.ACCEPTED);
	}

	@GetMapping("/getAccount/{accountId}")
	public Account getAccountByAccountid(@RequestHeader("Authorization") String token,@RequestHeader("Username") String username, @PathVariable int accountId) {
		if (token == null || token.length() == 0 || !util.validateToken(token,username)) {
			throw new TokenNotFoundException("Not Authorized");
		}
		return accountService.getAccountsByAccountId(token,username, accountId);
	}

	@PostMapping("/deposit/{accountId}/{amount}")
	public ResponseEntity<TransactionStatus> deposit(@RequestHeader("Authorization") String token,
			@RequestHeader("Username") String username,
			@PathVariable int accountId, @PathVariable Double amount) {
		if (token == null || token.length() == 0 || !util.validateToken(token,username)) {
			throw new TokenNotFoundException("Not Authorized");
		}
		TransactionStatus status = accountService.deposit(token, username,accountId, amount);
		return new ResponseEntity<>(status, HttpStatus.ACCEPTED);
	}

	@PostMapping("/withdraw/{accountId}/{amount}")
	public ResponseEntity<TransactionStatus> withdraw(@RequestHeader("Authorization") String token,
			@RequestHeader("Username") String username,
			@PathVariable int accountId, @PathVariable Double amount) {
		if (token == null || token.length() == 0 || !util.validateToken(token,username)) {
			throw new TokenNotFoundException("Not Authorized");
		}
		TransactionStatus status = accountService.withdraw(token,username, accountId, amount);
		return new ResponseEntity<>(status, HttpStatus.ACCEPTED);
	}

	@GetMapping("/getAllTransactions/{customerId}")
	public ResponseEntity<TransactionsHistory[]> getAccountStatement(@RequestHeader("Authorization") String token,
			@RequestHeader("Username") String username,
			@PathVariable int customerId) {
		if (token == null || token.length() == 0 || !util.validateToken(token,username)) {
			throw new TokenNotFoundException("Not Authorized");
		}

		TransactionsHistory[] status = accountService.getAccountStatement(token, username,customerId);

		return new ResponseEntity<>(status, HttpStatus.ACCEPTED);
	}

	@PostMapping("/transfer/{sourceAccountId}/{destinationAccountId}/{amount}")
	public ResponseEntity<TransactionStatus> withdraw(@RequestHeader("Authorization") String token,
			@RequestHeader("Username") String username,
			@PathVariable int sourceAccountId, @PathVariable int destinationAccountId, @PathVariable double amount) {
		if (token == null || token.length() == 0 || !util.validateToken(token,username)) {
			throw new TokenNotFoundException("Not Authorized");
		}
		TransactionStatus status = accountService.transfer(token, username,sourceAccountId, destinationAccountId, amount);
		return new ResponseEntity<>(status, HttpStatus.CREATED);
	}

}
