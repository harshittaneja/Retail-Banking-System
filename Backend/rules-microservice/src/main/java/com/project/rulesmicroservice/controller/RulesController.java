package com.project.rulesmicroservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.rulesmicroservice.model.RuleStatus;
import com.project.rulesmicroservice.service.RulesService;

@RestController
@CrossOrigin
@RequestMapping("/rules")
public class RulesController {
	
	
	@Autowired
	private RulesService rulesService;
	
	@GetMapping
	public String welcome() {
		return "Welcome";
	}
	
	@GetMapping("/evaluateMinBal/{amount}/{accountId}")
	public ResponseEntity<RuleStatus> evaluateMinBalance(@RequestHeader("Authorization") String token,@RequestHeader("Username") String username,@PathVariable Double amount,@PathVariable int accountId) {
		RuleStatus status=rulesService.evaluateMinBalance(token,username,amount, accountId);
		
		return new ResponseEntity<>(status,HttpStatus.ACCEPTED);
	}

}
