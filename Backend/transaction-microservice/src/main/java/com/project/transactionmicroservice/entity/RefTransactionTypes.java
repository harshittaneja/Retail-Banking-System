package com.project.transactionmicroservice.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RefTransactionTypes {
	
	@Id
	@GeneratedValue
	private int transactionTypeCode;
	private String transactionTypeDescription;
}
