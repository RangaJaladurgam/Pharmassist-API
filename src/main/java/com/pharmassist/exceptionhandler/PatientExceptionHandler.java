package com.pharmassist.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pharmassist.exception.NoPatientsFoundException;
import com.pharmassist.util.AppResponseBuilder;
import com.pharmassist.util.ErrorStructure;

@RestControllerAdvice
public class PatientExceptionHandler {

	private final AppResponseBuilder response;

	public PatientExceptionHandler(AppResponseBuilder response) {
		super();
		this.response = response;
	}

	@ExceptionHandler(NoPatientsFoundException.class)
	public ResponseEntity<ErrorStructure<String>> handleNoPatientsFound(NoPatientsFoundException ex){
		return response.error(HttpStatus.NOT_FOUND, ex.getMessage(), "No patients found");
	}
}
