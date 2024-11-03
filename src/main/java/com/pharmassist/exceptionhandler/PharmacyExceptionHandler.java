package com.pharmassist.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pharmassist.exception.PharmacyNotFoundByIdException;
import com.pharmassist.util.AppResponseBuilder;
import com.pharmassist.util.ErrorStructure;

@RestControllerAdvice
public class PharmacyExceptionHandler {
	
	private final AppResponseBuilder response;
	
	public PharmacyExceptionHandler(AppResponseBuilder response) {
		super();
		this.response = response;
	}

	@ExceptionHandler(PharmacyNotFoundByIdException.class)
	public ResponseEntity<ErrorStructure<String>> handlePharmacyNotFoundById(PharmacyNotFoundByIdException ex){
		return response.error(HttpStatus.NOT_FOUND, ex.getMessage(), "Pharmacy Not found By ID"); 
	}
}
