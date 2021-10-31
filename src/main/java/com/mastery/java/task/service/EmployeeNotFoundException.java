package com.mastery.java.task.service;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(String message) {
		super(message);
	}
}
