package com.pharmassist.exception;


public class NoPatientsFoundException extends RuntimeException {
	
	private String message;

	public NoPatientsFoundException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	
	
}
