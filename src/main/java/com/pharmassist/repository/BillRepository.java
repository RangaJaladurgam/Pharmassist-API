package com.pharmassist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pharmassist.entity.Bill;

public interface BillRepository extends JpaRepository<Bill, String>{

}
