package com.project.transactionmicroservice.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionsHistory {
	
	private int transactionId;
	private String trsansactionStatus;
	private String transactionType;
	private Date transactionDate;
	private String transactionMethod;
	private String counterparties;
	private Double amountOfTransaction;
	private int accountId;
}
