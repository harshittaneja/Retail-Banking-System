package com.project.Account.Microservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.Account.Microservice.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{
	public List<Account>getAccountByCustomerId(int customerId);
	public Optional<Account> getAccountByAccountId(int accountId);

}
