package com.project.Account.Microservice.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.Account.Microservice.entity.Account;
import com.project.Account.Microservice.entity.AccountCreationStatus;
import com.project.Account.Microservice.model.TransactionStatus;
import com.project.Account.Microservice.model.TransactionsHistory;
import com.project.Account.Microservice.service.AccountService;
import com.project.Account.Microservice.service.impl.AccountServiceImpl;
import com.project.Account.Microservice.utils.JWTUtil;

@WebMvcTest(controllers = AccountController.class)
class AccountControllerTest {

	@MockBean
	private AccountService accountService;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@MockBean
	private JWTUtil util;

	@Test
	public void shouldListAccounts() throws Exception {

		Account account1 = new Account(1, 1, "savings", 0.0);
		Account account2 = new Account(2, 1, "current", 0.0);
		List<Account> accounts = List.of(account1, account2);
		String token = "fhibwehfbwefbhiebfiefbief";
		String username = "deep";
		Mockito.when(util.validateToken(token, username)).thenReturn(true);
		Mockito.when(accountService.getCustomerAccounts(token, username, 1)).thenReturn(accounts);
		mockMvc.perform(MockMvcRequestBuilders.get("/account/getCustomerAccounts/{customerId}", 1)
				.header("Authorization", token).header("Username", username)).andExpect(MockMvcResultMatchers.status().is(202)).andDo(print());

	}

	@Test
	public void shouldCreateAccount() throws Exception {

		Account account = new Account(1, 1, "savings", 0.0);
		String token = "fhibwehfbwefbhiebfiefbief";
		String username = "deep";
		Mockito.when(util.validateToken(token, username)).thenReturn(true);
		Mockito.when(accountService.getAccountsByAccountId(token, username, 1)).thenReturn(account);
		mockMvc.perform(MockMvcRequestBuilders.post("/account/createAccount/{customerId}", 1)
				.header("Authorization", token).header("Username", username))
				.andExpect(MockMvcResultMatchers.status().is(201));
	}

	@Test
	public void shouldReturnAccountById() throws Exception {

		Account account = new Account(1, 1, "savings", 0.0);

		String token = "fhibwehfbwefbhiebfiefbief";
		String username = "deep";
		Mockito.when(util.validateToken(token, username)).thenReturn(true);
		Mockito.when(accountService.getAccountsByAccountId(token, username, 1)).thenReturn(account);
		mockMvc.perform(MockMvcRequestBuilders.get("/account/getAccount/{accountId}", 1).header("Authorization", token)
				.header("Username", username)).andExpect(MockMvcResultMatchers.status().is(200));
	}

	@Test
	public void shouldDeposit() throws Exception {

		Account account = new Account(1, 1, "savings", 0.0);
		TransactionStatus status = new TransactionStatus("Successfull");
		String token = "fhibwehfbwefbhiebfiefbief";
		String username = "deep";
		Mockito.when(util.validateToken(token, username)).thenReturn(true);
		Mockito.when(accountService.deposit(token, username, 1, 1000d)).thenReturn(status);
		mockMvc.perform(MockMvcRequestBuilders.post("/account/withdraw/{accountId}/{amount}", 1, 1000d)
				.header("Authorization", token).header("Username", username))
				.andExpect(MockMvcResultMatchers.status().is(202));
	}

	@Test
	public void shouldWithdraw() throws Exception {

		Account account = new Account(1, 1, "savings", 0.0);
		TransactionStatus status = new TransactionStatus("Successfull");
		String token = "fhibwehfbwefbhiebfiefbief";
		String username = "deep";
		Mockito.when(util.validateToken(token, username)).thenReturn(true);
		Mockito.when(accountService.deposit(token, username, 1, 1000d)).thenReturn(status);
		mockMvc.perform(MockMvcRequestBuilders.post("/account/deposit/{accountId}/{amount}", 1, 1000d)
				.header("Authorization", token).header("Username", username))
				.andExpect(MockMvcResultMatchers.status().is(202));
	}

	@Test
	public void shouldReturnAllAccountsByCustomerId() throws Exception {

		TransactionsHistory t1 = new TransactionsHistory();
		TransactionsHistory t2 = new TransactionsHistory();
		TransactionsHistory[] status = { t1, t2 };
		String token = "fhibwehfbwefbhiebfiefbief";
		String username = "deep";
		Mockito.when(util.validateToken(token, username)).thenReturn(true);
		Mockito.when(accountService.getAccountStatement(token, username, 1)).thenReturn(status);
		mockMvc.perform(MockMvcRequestBuilders.get("/account/getAllTransactions/{customerId}", 1)
				.header("Authorization", token).header("Username", username))
				.andExpect(MockMvcResultMatchers.status().is(202));
	}

	@Test
	public void transferMoney() throws Exception {

		TransactionStatus status = new TransactionStatus("Successfull");
		String token = "fhibwehfbwefbhiebfiefbief";
		String username = "deep";
		Mockito.when(util.validateToken(token, username)).thenReturn(true);
		Mockito.when(accountService.transfer(token, username, 1, 2, 1000d)).thenReturn(status);
		mockMvc.perform(MockMvcRequestBuilders
				.post("/account/transfer/{sourceAccountId}/{destinationAccountId}/{amount}", 1, 2, 1000d)
				.header("Authorization", token).header("Username", username))
				.andExpect(MockMvcResultMatchers.status().is(201));
	}

}
