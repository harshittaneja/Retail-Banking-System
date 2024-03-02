package com.project.customermicroservice.service;


import com.project.customermicroservice.model.CustomerCreationStatus;
import com.project.customermicroservice.model.CustomerRequest;
import com.project.customermicroservice.model.CustomerResponse;

public interface CustomerService {
	
	public CustomerCreationStatus createCustomer(String token,String username,CustomerRequest customerRequest);
	public CustomerResponse getCustomerDetails(int customerId);
}
