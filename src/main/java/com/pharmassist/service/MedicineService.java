package com.pharmassist.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pharmassist.entity.Admin;
import com.pharmassist.entity.Medicine;
import com.pharmassist.entity.Pharmacy;
import com.pharmassist.enums.Form;
import com.pharmassist.exception.AdminNotFoundByIdException;
import com.pharmassist.exception.NoMedicinesFoundException;
import com.pharmassist.exception.PharmacyNotFoundByIdException;
import com.pharmassist.mapper.MedicineMapper;
import com.pharmassist.repository.AdminRepository;
import com.pharmassist.repository.MedicineRepository;
import com.pharmassist.repository.PharmacyRepository;
import com.pharmassist.responsedto.MedicineResponse;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class MedicineService {
		
	private final MedicineRepository medicineRepository;
	private final PharmacyRepository pharmacyRepository;
	private final MedicineMapper medicineMapper;
	private final AdminRepository adminRepository;

	public MedicineService(MedicineRepository medicineRepository, PharmacyRepository pharmacyRepository, MedicineMapper medicineMapper, AdminRepository adminRepository) {
		super();
		this.medicineRepository = medicineRepository;
		this.pharmacyRepository = pharmacyRepository;
		this.medicineMapper = medicineMapper;
		this.adminRepository = adminRepository;
	}

	@Transactional
	public String uploadMedicines(MultipartFile file,String email) {
		List<Medicine> medicines = new ArrayList<>();
		Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new AdminNotFoundByIdException("Admin not found"));
		if(admin.getPharmacy()==null)
			throw new PharmacyNotFoundByIdException("Pharmacy is not Linked to Admin");
	    String pharmacyId = admin.getPharmacy()
	                                       .getPharmacyId();
				
		Pharmacy pharmacy = pharmacyRepository.findById(pharmacyId)
					.orElseThrow(()-> new PharmacyNotFoundByIdException("Failed to Add Medicines because no pharmacy found by Id: "+pharmacyId));
		
		try (XSSFWorkbook workBook = new XSSFWorkbook(file.getInputStream())) {
			for (Sheet sheet : workBook) {
				for (Row row : sheet) {
					if (row.getRowNum() != 0) {
						Medicine medicine = validatedMedicines(row);
						medicine.setPharmacy(pharmacy);
						saveMedicine(medicine);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "uploaded Successfully!";
	}

	public void saveMedicine(@Valid Medicine medicine) {
		medicineRepository.save(medicine);
	}

	public Medicine validatedMedicines(Row row) {
		Medicine medicine = new Medicine();
		try {
			medicine.setName(row.getCell(0).getStringCellValue());
			medicine.setCategory(row.getCell(1).getStringCellValue());
			medicine.setDosageInMg((int) row.getCell(2).getNumericCellValue());
			medicine.setForm(Form.valueOf((row.getCell(3).getStringCellValue().toUpperCase())));
			medicine.setIngredients(row.getCell(4).getStringCellValue());
			medicine.setManufacturer(row.getCell(5).getStringCellValue());
			medicine.setPrice(row.getCell(6).getNumericCellValue());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			medicine.setExpiryDate(LocalDate.parse(row.getCell(7).getStringCellValue(),formatter));
			medicine.setStockQuantity((int) row.getCell(8).getNumericCellValue());

		} catch (NullPointerException | IllegalStateException | DateTimeParseException e) {
			System.out.println(e.getMessage());
		}

		return medicine;

	}

	public List<MedicineResponse> findMedicineByNameOrIngredients(String userInput) {
		List<Medicine> medicines = medicineRepository.findMedicineByNameOrIngredient(userInput);
						
		if(medicines.isEmpty())
			throw new NoMedicinesFoundException("No Medicines Found by provided Input");
		
		return medicines.stream()
				.map(medicineMapper::mapToMedicineResponse)
				.toList();
	}

	public List<MedicineResponse> findAllMedicines(String email) {
		Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new AdminNotFoundByIdException("Admin not found"));
		if(admin.getPharmacy()==null)
			throw new PharmacyNotFoundByIdException("Pharmacy is not Linked to Admin");
	    String pharmacyId = admin.getPharmacy()
	                                       .getPharmacyId();

	    return medicineRepository.findAll().stream()
	        .filter(medicine -> medicine.getPharmacy().getPharmacyId().equals(pharmacyId))
	        .map(medicineMapper::mapToMedicineResponse)
	        .collect(Collectors.toList());
	}


	
	
}
