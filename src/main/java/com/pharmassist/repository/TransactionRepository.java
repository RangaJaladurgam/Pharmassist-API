package com.pharmassist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pharmassist.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, String>{
	
}
