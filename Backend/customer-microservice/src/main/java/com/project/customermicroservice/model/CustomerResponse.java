package com.project.customermicroservice.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {
	private int customerId;
	private String address;
	private String dob;
	private String panNumber;
	private String name;
	private String username;
	private List<Integer>accountNumbers;
	
}
