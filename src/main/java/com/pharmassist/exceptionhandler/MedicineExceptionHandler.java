package com.pharmassist.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pharmassist.exception.NoMedicinesFoundException;
import com.pharmassist.util.AppResponseBuilder;
import com.pharmassist.util.ErrorStructure;

@RestControllerAdvice
public class MedicineExceptionHandler {
	
	private final AppResponseBuilder response;
	
	public MedicineExceptionHandler(AppResponseBuilder response) {
		super();
		this.response = response;
	}

	@ExceptionHandler(NoMedicinesFoundException.class)
	public ResponseEntity<ErrorStructure<String>> handleNoMedicinesFound(NoMedicinesFoundException ex){
		return response.error(HttpStatus.NOT_FOUND,ex.getMessage(), "No Medicines Found");
	}
	
}
