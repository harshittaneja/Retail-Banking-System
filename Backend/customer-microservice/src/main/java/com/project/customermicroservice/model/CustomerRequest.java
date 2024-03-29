package com.project.customermicroservice.model;

import java.util.List;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {
	
	private String name;
	private String username;
	private String address;
	private String dob;
	private String panNumber;
	
}
