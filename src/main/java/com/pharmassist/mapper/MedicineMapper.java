package com.pharmassist.mapper;

import org.springframework.stereotype.Component;

import com.pharmassist.entity.Medicine;
import com.pharmassist.responsedto.MedicineResponse;

@Component
public class MedicineMapper {
	
	private PharmacyMapper pharmacyMapper;
	
	
	
	public MedicineMapper(PharmacyMapper pharmacyMapper) {
		super();
		this.pharmacyMapper = pharmacyMapper;
	}



	public MedicineResponse mapToMedicineResponse(Medicine medicine) {
		MedicineResponse medicineResponse = new MedicineResponse();
		medicineResponse.setMedicineId(medicine.getMedicineId());
		medicineResponse.setName(medicine.getName());
		medicineResponse.setCategory(medicine.getCategory());
		medicineResponse.setDosageInMg(medicine.getDosageInMg());
		medicineResponse.setForm(medicine.getForm());
		medicineResponse.setIngredients(medicine.getIngredients());
		medicineResponse.setManufacturer(medicine.getManufacturer());
		medicineResponse.setExpiryDate(medicine.getExpiryDate());
		medicineResponse.setStockQuantity(medicine.getStockQuantity());
		medicineResponse.setPrice(medicine.getPrice());
		medicineResponse.setPharmacyResponse(pharmacyMapper.mapToPharmacyResponse(medicine.getPharmacy()));
		return medicineResponse;
	}
}
