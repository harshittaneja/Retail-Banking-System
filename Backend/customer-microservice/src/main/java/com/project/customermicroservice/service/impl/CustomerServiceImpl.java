package com.project.customermicroservice.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.project.customermicroservice.entity.Account;
import com.project.customermicroservice.entity.Customer;
import com.project.customermicroservice.model.AccountCreationStatus;
import com.project.customermicroservice.model.CustomerCreationStatus;
import com.project.customermicroservice.model.CustomerRequest;
import com.project.customermicroservice.model.CustomerResponse;
import com.project.customermicroservice.repository.CustomerRepository;
import com.project.customermicroservice.service.CustomerService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public CustomerCreationStatus createCustomer(String token,String username, CustomerRequest customerRequest) {
		HttpEntity<String> request = new HttpEntity<String>("J");

		Customer customer = new Customer();
		Customer savedCustomer = customerRepository.save(customer);
		RestTemplate restTemplate = new RestTemplate();

		// create headers
		HttpHeaders headers = new HttpHeaders();

		// set `Content-Type` and `Accept` headers
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		// example of custom header
		headers.set("Authorization", token);
		headers.set("Username", username);
		System.out.println(token);
		// build the request
		HttpEntity request1 = new HttpEntity(headers);

		String url = "http://localhost:9001/account/createAccount/" + savedCustomer.getCustomerId();
		ResponseEntity<AccountCreationStatus> response = restTemplate.exchange(url, HttpMethod.POST, request1,
				AccountCreationStatus.class

		);
		AccountCreationStatus status=response.getBody();

		List<Integer> accountNumbers = status.getAccountId();
		List<Account> accountList = new ArrayList<Account>();
		for (int accountNum : accountNumbers) {
			Account account = new Account();
			account.setAccountNumber(accountNum);
			account.setCustomer(savedCustomer);
			accountList.add(account);
		}

		customer.setAccountNumbers(accountList);
		customer.setAddress(customerRequest.getAddress());
		customer.setDob(customerRequest.getDob());
		customer.setPanNumber(customerRequest.getPanNumber());
		customer.setName(customerRequest.getName());
		customer.setUsername(customerRequest.getUsername());
		savedCustomer = customerRepository.save(customer);

		CustomerCreationStatus resultStatus = new CustomerCreationStatus();
		resultStatus.setCustomerId(savedCustomer.getCustomerId());
		resultStatus.setMsg("Customer with the id :" + savedCustomer.getCustomerId() + " is saved");
		resultStatus.setAccountNumbers(accountNumbers);
		return resultStatus;

	}

	@Override
	public CustomerResponse getCustomerDetails(int customerId) {
		Optional<Customer> customerOpt = customerRepository.findById(customerId);
		if (customerOpt.isPresent()) {
			return mapToCustomerResponse(customerOpt.get());
		}

		return null;
	}

	public CustomerResponse mapToCustomerResponse(Customer customer) {
		CustomerResponse customerResponse = new CustomerResponse();
		List<Account> accountList = customer.getAccountNumbers();
		List<Integer> accountNumbers = new ArrayList<Integer>();
		for (Account account : accountList) {
			accountNumbers.add(account.getAccountNumber());
		}
		customerResponse.setAccountNumbers(accountNumbers);
		customerResponse.setCustomerId(customer.getCustomerId());
		customerResponse.setDob(customer.getDob());
		customerResponse.setPanNumber(customer.getPanNumber());
		customerResponse.setAddress(customer.getAddress());
		customerResponse.setName(customer.getName());
		customerResponse.setUsername(customer.getUsername());
		return customerResponse;

	}

}
