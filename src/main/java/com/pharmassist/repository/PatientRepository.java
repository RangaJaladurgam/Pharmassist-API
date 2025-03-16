package com.pharmassist.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pharmassist.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, String> {
	
	@Query("SELECT p FROM Patient p WHERE p.pharmacy.pharmacyId = :pharmacyId")
	public List<Patient> findPatientsByPharmacy(@Param("pharmacyId") String pharmacyId);
	
	@Query("SELECT p FROM Patient p WHERE p.pharmacy.pharmacyId = :pharmacyId and p.phoneNumber = :phoneNumber")
	public Optional<Patient> findByPhoneNumber(@Param("pharmacyId") String pharmacyId,@Param("phoneNumber") String phoneNumber);
	
}	
