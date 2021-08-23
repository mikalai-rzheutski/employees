package com.mastery.java.task.rest.exceptions;

public class NotFoundException extends RuntimeException {
	public NotFoundException() {
		super();
	}

	public NotFoundException(String message) {
		super(message);
	}
}
