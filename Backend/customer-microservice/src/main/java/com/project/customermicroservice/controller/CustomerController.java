package com.project.customermicroservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.customermicroservice.entity.Customer;
import com.project.customermicroservice.exception.TokenNotFoundException;
import com.project.customermicroservice.model.CustomerCreationStatus;
import com.project.customermicroservice.model.CustomerRequest;
import com.project.customermicroservice.model.CustomerResponse;
import com.project.customermicroservice.service.CustomerService;
import com.project.customermicroservice.utils.JWTUtil;

@RestController
@CrossOrigin
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	@Autowired
	private JWTUtil util;

	@PostMapping("/createCustomer")
	public ResponseEntity<CustomerCreationStatus> createCustomer(@RequestHeader("Authorization") String token,
			@RequestHeader("Username") String username,
			@RequestBody CustomerRequest customerRequest) throws Exception {
		
		if (token == null || token.length() == 0 || !util.validateToken(token,username)) {
			
			throw new TokenNotFoundException("Not Authorized");
		}
		CustomerCreationStatus status = customerService.createCustomer(token,username,customerRequest);
		return new ResponseEntity<>(status, HttpStatus.CREATED);
	}

	@GetMapping("/getCustomerDetails/{customerId}")
	public ResponseEntity<CustomerResponse> getCustomerDetails(@RequestHeader("Authorization") String token,
			@RequestHeader("Username") String username,
			@PathVariable int customerId) throws Exception {
		if (token == null || token.length() == 0 || !util.validateToken(token,username)) {
			throw new TokenNotFoundException("Not Authorized");
		}

		CustomerResponse customer = customerService.getCustomerDetails(customerId);
		System.out.println(customer.getAddress());
		return new ResponseEntity<>(customer, HttpStatus.ACCEPTED);
	}

}
