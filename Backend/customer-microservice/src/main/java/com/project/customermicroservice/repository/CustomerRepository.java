package com.project.customermicroservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.customermicroservice.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
