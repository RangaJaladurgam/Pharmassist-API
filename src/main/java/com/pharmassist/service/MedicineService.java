package com.pharmassist.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pharmassist.entity.Medicine;
import com.pharmassist.entity.Pharmacy;
import com.pharmassist.enums.Form;
import com.pharmassist.exception.NoMedicinesFoundException;
import com.pharmassist.exception.PharmacyNotFoundByIdException;
import com.pharmassist.mapper.MedicineMapper;
import com.pharmassist.repository.MedicineRepository;
import com.pharmassist.repository.PharmacyRepository;
import com.pharmassist.responsedto.MedicineResponse;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class MedicineService {
		
	private final MedicineRepository medicineRepository;
	private final PharmacyRepository pharmacyRepository;
	public final MedicineMapper medicineMapper;

	public MedicineService(MedicineRepository medicineRepository, PharmacyRepository pharmacyRepository, MedicineMapper medicineMapper) {
		super();
		this.medicineRepository = medicineRepository;
		this.pharmacyRepository = pharmacyRepository;
		this.medicineMapper = medicineMapper;
	}

	@Transactional
	public String uploadMedicines(MultipartFile file,String pharmacyId) {
		List<Medicine> medicines = new ArrayList<>();
		
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
			medicine.setExpiryDate(LocalDate.parse(row.getCell(7).getStringCellValue()));
			medicine.setStockQuantity((int) row.getCell(8).getNumericCellValue());

		} catch (NullPointerException | IllegalStateException | DateTimeParseException e) {
			throw new IllegalArgumentException("Invalid data in row " + row.getRowNum(), e);
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

	
	
}
