package com.project.transactionmicroservice.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.project.transactionmicroservice.model.TransactionStatus;
import com.project.transactionmicroservice.model.TransactionsHistory;
import com.project.transactionmicroservice.service.TransactionService;

@WebMvcTest(controllers = TransactionController.class)
class TransactionControllerTest {

	@MockBean
	private TransactionService service;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void shouldDeposit() throws Exception {

		TransactionStatus status = new TransactionStatus("Successfull");
		String token = "fhibwehfbwefbhiebfiefbief";
		String username = "deep";
//		Mockito.when(util.validateToken(token, username)).thenReturn(true);
		Mockito.when(service.deposit(token, username, 1, 1000d)).thenReturn(status);
		mockMvc.perform(MockMvcRequestBuilders.post("/transaction/deposit/{accountId}/{amount}", 1, 1000d)
				.header("Authorization", token).header("Username", username))
				.andExpect(MockMvcResultMatchers.status().is(201)).andDo(print());

	}

	@Test
	public void shouldWithdraw() throws Exception {

		TransactionStatus status = new TransactionStatus("Successfull");
		String token = "fhibwehfbwefbhiebfiefbief";
		String username = "deep";
//		Mockito.when(util.validateToken(token, username)).thenReturn(true);
		Mockito.when(service.withdraw(token, username, 1, 1000d)).thenReturn(status);
		mockMvc.perform(MockMvcRequestBuilders.post("/transaction/withdraw/{accountId}/{amount}", 1, 1000d)
				.header("Authorization", token).header("Username", username))
				.andExpect(MockMvcResultMatchers.status().is(201)).andDo(print());

	}

	@Test
	public void shouldTransfer() throws Exception {

		TransactionStatus status = new TransactionStatus("Successfull");
		String token = "fhibwehfbwefbhiebfiefbief";
		String username = "deep";
//		Mockito.when(util.validateToken(token, username)).thenReturn(true);
		Mockito.when(service.transfer(token, username, 1, 2, 1000d)).thenReturn(status);
		mockMvc.perform(MockMvcRequestBuilders
				.post("/transaction/transfer/{sourceAccountId}/{destinationAccountId}/{amount}", 1, 2, 1000d)
				.header("Authorization", token).header("Username", username))
				.andExpect(MockMvcResultMatchers.status().is(201)).andDo(print());

	}

	@Test
	public void getAllTransactions() throws Exception {

		TransactionsHistory history = new TransactionsHistory();
		String token = "fhibwehfbwefbhiebfiefbief";
		String username = "deep";
//		Mockito.when(util.validateToken(token, username)).thenReturn(true);
		Mockito.when(service.getAllTransactions(token, username, 1)).thenReturn(List.of(history));
		mockMvc.perform(MockMvcRequestBuilders.get("/transaction/getAllTransactions/{customerId}", 1)
				.header("Authorization", token).header("Username", username))
				.andExpect(MockMvcResultMatchers.status().is(202)).andDo(print());

	}
}
