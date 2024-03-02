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
public class RefPaymentMethods {
	
	@Id
	@GeneratedValue
	private int refPaymentCode;
	private String paymentMethodName;
}
