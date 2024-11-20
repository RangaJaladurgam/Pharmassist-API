package com.pharmassist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pharmassist.entity.BillItem;

public interface BillItemRepository extends JpaRepository<BillItem, String>  {
	
}
