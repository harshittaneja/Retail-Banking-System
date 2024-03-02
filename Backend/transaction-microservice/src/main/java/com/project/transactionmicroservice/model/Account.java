package com.project.transactionmicroservice.model;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
	
	
	private int accountId;
	private int customerId;
	private String accountType;
	private int balance;

}