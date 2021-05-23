package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;

public class SalespersonException extends Exception {

	public SalespersonException(String s) {
		super(s);
	}
}
