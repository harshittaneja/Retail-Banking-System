package com.project.rulesmicroservice.service.impl;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.project.rulesmicroservice.model.Account;
import com.project.rulesmicroservice.model.RuleStatus;
import com.project.rulesmicroservice.service.RulesService;

@Service
public class RulesServiceImpl implements RulesService{
	
	private int fixedBalance=1000;
	@Autowired
	private RestTemplate restTemplate;
	@Override
	public RuleStatus evaluateMinBalance(String token,String username,Double balance, int accountId) {
		HttpHeaders headers = new HttpHeaders();

		// set `Content-Type` and `Accept` headers
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		// example of custom header
		headers.set("Authorization", token);
		headers.set("Username", username);

		// build the request
		HttpEntity request = new HttpEntity(headers);
		
		String url="http://localhost:9001/account/getAccount/"+accountId;
		ResponseEntity<Account> response = restTemplate.exchange(
		        url,
		        HttpMethod.GET,
		        request,
		        Account.class
		        
		);

		Account account=response.getBody();

		String accountType=account.getAccountType();
		Double updatedBalance=account.getBalance()-balance;
		RuleStatus status=new RuleStatus();
		if(accountType.equals("savings") && updatedBalance<fixedBalance) {
			status.setStatus("DENIED");
		}else if(accountType.equals("savings") && updatedBalance>=fixedBalance) {
			status.setStatus("ALLOWED");
		}else {
			status.setStatus("NA");
		}
		return status;
	}

}
