package com.project.rulesmicroservice.service;

import com.project.rulesmicroservice.model.RuleStatus;

public interface RulesService {
	public RuleStatus evaluateMinBalance(String token,String username,Double balance,int accountId);
}
