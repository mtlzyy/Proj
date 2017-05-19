package com.zyy.exception;

public class FormatInvalidException extends Exception {

	@Override
	public void printStackTrace() {
		System.out.println("Wrong Format");
	}
	
}
