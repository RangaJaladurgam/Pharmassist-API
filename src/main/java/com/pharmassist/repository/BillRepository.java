package com.pharmassist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pharmassist.entity.Bill;

public interface BillRepository extends JpaRepository<Bill, String>{
	
	
	@Query("SELECT b FROM Bill b,Patient p WHERE b.patient.patientId = p.patientId AND p.pharmacy.pharmacyId = :pharmacyId AND b.totalPayableAmount != 0")
	public List<Bill> findAllBillsByPharamacy(@Param("pharmacyId") String pharmacyId);
}
