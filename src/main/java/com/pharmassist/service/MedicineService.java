package com.pharmassist.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import com.pharmassist.exception.PharmacyNotFoundByIdException;
import com.pharmassist.repository.MedicineRepository;
import com.pharmassist.repository.PharmacyRepository;

import jakarta.transaction.Transactional;

@Service
public class MedicineService {
		
	private final MedicineRepository medicineRepository;
	private final PharmacyRepository pharmacyRepository;

	public MedicineService(MedicineRepository medicineRepository, PharmacyRepository pharmacyRepository) {
		super();
		this.medicineRepository = medicineRepository;
		this.pharmacyRepository = pharmacyRepository;
	}

	@Transactional
	public String uploadMedicines(MultipartFile file,String pharmacyId) {
		List<Medicine> medicines = new ArrayList<>();
		
		Pharmacy pharmacy = pharmacyRepository.findById(pharmacyId)
					.orElseThrow(()-> new PharmacyNotFoundByIdException("Failed to Add Medicines because no pharmacy found by Id: "+pharmacyId));
		
		try (XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream())){
			for(Sheet sheet : workbook) {
                for(Row row : sheet) {
                    if(row.getRowNum() != 0) {
                    	Medicine medicine = new Medicine();
                    	
                    	String name = row.getCell(0).getStringCellValue();
                    	medicine.setName(name);
                    	String category = row.getCell(1).getStringCellValue();
                    	medicine.setCategory(category);
                    	int dosageInMg = (int) row.getCell(2).getNumericCellValue();
                    	medicine.setDosageInMg(dosageInMg);
                    	String ingredients = row.getCell(4).getStringCellValue();
                    	medicine.setIngredients(ingredients);
                    	Form form = Form.valueOf(row.getCell(3).getStringCellValue().toUpperCase());
                    	medicine.setForm(form);
                    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    	LocalDate date = LocalDate.parse(row.getCell(7).getStringCellValue(),formatter);
                    	medicine.setExpiryDate(date);
                    	String manufacturer = row.getCell(5).getStringCellValue();
                    	medicine.setManufacturer(manufacturer);
                    	medicine.setStockQuantity(10);
                    	
                    	double price = row.getCell(6).getNumericCellValue() * 84.12;
                    	price = Math.round(price * 100.0) / 100.0;
                    	medicine.setPrice(price);
                    	
                    	medicine.setPharmacy(pharmacy);
                    	System.out.println(medicine);
                    	
                    	medicines.add(medicine);
                    }
                    	
                }
            }
			

		} catch (IOException e) {
			e.printStackTrace();
		}
		medicineRepository.saveAll(medicines);
		return "uploaded Successfully!";
	}

	
	
}
