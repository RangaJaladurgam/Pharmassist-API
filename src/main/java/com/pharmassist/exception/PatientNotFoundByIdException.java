package com.pharmassist.exception;

public class PatientNotFoundByIdException extends RuntimeException{

	private String message;

	public PatientNotFoundByIdException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
	
}
