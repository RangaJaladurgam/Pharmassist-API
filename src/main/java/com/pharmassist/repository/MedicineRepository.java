package com.pharmassist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pharmassist.entity.Medicine;

public interface MedicineRepository extends JpaRepository<Medicine, String>{
	
	@Query("SELECT m FROM Medicine m WHERE m.name LIKE %:userInput% OR m.ingredients LIKE %:userInput%")
	public List<Medicine> findMedicineByNameOrIngredient(@Param("userInput") String userInput);
}	
