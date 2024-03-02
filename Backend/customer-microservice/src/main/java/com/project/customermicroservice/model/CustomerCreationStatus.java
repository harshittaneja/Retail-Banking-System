package com.project.customermicroservice.model;

import java.util.List;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CustomerCreationStatus {
	private String msg;
	private int customerId;
	private List<Integer>accountNumbers;
}
