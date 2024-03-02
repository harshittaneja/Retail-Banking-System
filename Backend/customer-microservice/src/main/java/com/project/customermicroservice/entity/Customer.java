package com.project.customermicroservice.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Customer {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int customerId;
	private String address;
	private String name;
	private String username;
	private String dob;
	private String panNumber;
	@OneToMany(targetEntity=Account.class, mappedBy="customer", fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	List<Account>accountNumbers;
}
