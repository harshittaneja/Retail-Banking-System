package com.project.transactionmicroservice.entity;

import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FinancialTransactions {

	@Id
	@GeneratedValue
	private int transactionId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "counterpart_id", referencedColumnName = "counterpartyId")
	private Counterparties counterparties;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "payment_method_code", referencedColumnName = "refPaymentCode")
	private RefPaymentMethods refPaymentMethods;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "transaction_type_code", referencedColumnName = "transactionTypeCode")
	private RefTransactionTypes refTransactionTypes;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "transaction_status_code", referencedColumnName = "transactionStatusCode")
	private RefTransactionStatus refTransactionStatus;

	@CreationTimestamp
	private Date dateofTransaction;
	private int accountId;
	private int customerId;
	private Double amountOfTransaction;

}
