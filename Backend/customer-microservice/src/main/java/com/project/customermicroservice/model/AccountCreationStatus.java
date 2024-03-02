package com.project.customermicroservice.model;

import java.util.List;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountCreationStatus {
	
	private String message;
	private List<Integer> accountId;
}
