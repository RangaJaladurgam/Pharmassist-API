package com.pharmassist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class AppDoc {
	
	
	Info info() {
	    return new Info()
	            .title("Pharmassist API - Pharmacy Management System")
	            .description("🌐 Pharmassist RESTful API\n\n"
	                    + "Description:\n"
	                    + "Efficient. Secure. Scalable.\n"
	                    + "The Pharmassist API is your all-in-one solution for streamlined pharmacy management and patient care.\n\n"
	                    + "💼 Core Functionalities:\n"
	                    + "• Admins: Control and manage access, oversee pharmacy operations.\n"
	                    + "• Pharmacies: Manage branch details, stock levels, and more.\n"
	                    + "• Medicines: Maintain inventory, track availability, and manage stock updates.\n"
	                    + "• Transactions: Record and process purchases, handle patient interactions.\n"
	                    + "• Bills: Generate and manage billing records with accuracy.\n"
	                    + "• Patients: Track patient histories, prescriptions, and medication interactions.\n\n"
	                    + "This API empowers developers to build and integrate robust pharmacy solutions. "
	                    + "With RESTful services designed for accuracy, efficiency, and scale, Pharmassist is ideal for healthcare "
	                    + "platforms focused on delivering reliable, patient-centered service.\n\n"
	                    + "Explore, integrate, and elevate pharmacy care with Pharmassist.")
	            .version("v1")
	            .contact(contact());
	}

	
	Contact contact() {
		return new Contact()
				.email("rangajaladurgam7258@gmail.com")
				.name("Raj")
				.url("https://rangajaladurgam.com");
	}
	
	@Bean
	OpenAPI openAPI() {
		return new OpenAPI().info(info());
	}
}
