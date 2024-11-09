package com.pharmassist.entity;

import java.time.LocalDate;

import com.pharmassist.config.GenerateCustomId;
import com.pharmassist.enums.Form;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

@Entity
public class Medicine {
	
	@Id
	@GenerateCustomId
	private String medicineId;
	@NotNull(message = "Name cannot be null")
	@NotBlank(message = "Name cannot be blank")
	@Pattern(regexp = "^[A-Za-z\\s]{2,}$",message = "Name must contain only letters and spaces, with a minimum of 2 characters.")
	private String name;
	
	private String category;
	
	@NotNull(message = "Ingredients cannot be null")
	@NotBlank(message = "Ingredients cannot be blank")
	private String ingredients;
	
	@Positive(message = "Dosage must be a positive value")
	private int dosageInMg;
	
	@NotNull(message = "form cannot be null")
	@Enumerated(EnumType.STRING)
	private Form form;
	
	@NotNull(message = "Manufacturer cannot be null")
	@NotBlank(message = "Manufacturer cannot be blank")
	private String manufacturer;
	
	@Min(value = 0, message = "Stock quantity cannot be negative")
	private int stockQuantity;
	
	@Future(message = "Expiry date must be a future date")
	private LocalDate expiryDate;
	
	@Positive(message = "Price must be positive")
	private double price;
	
	@ManyToOne
	private Pharmacy pharmacy;

	public String getMedicineId() {
		return medicineId;
	}

	public void setMedicineId(String medicineId) {
		this.medicineId = medicineId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getIngredients() {
		return ingredients;
	}

	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}

	public int getDosageInMg() {
		return dosageInMg;
	}

	public void setDosageInMg(int dosageInMg) {
		this.dosageInMg = dosageInMg;
	}

	public Form getForm() {
		return form;
	}

	public void setForm(Form form) {
		this.form = form;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public int getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}

	@Override
	public String toString() {
		return "Medicine [medicineId=" + medicineId + ", name=" + name + ", category=" + category + ", ingredients="
				+ ingredients + ", dosageInMg=" + dosageInMg + ", form=" + form + ", manufacturer=" + manufacturer
				+ ", stockQuantity=" + stockQuantity + ", expiryDate=" + expiryDate + ", price=" + price + ", pharmacy="
				+ pharmacy + "]";
	}
	
	
	
}
