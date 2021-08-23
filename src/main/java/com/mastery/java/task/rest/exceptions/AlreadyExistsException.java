package com.mastery.java.task.rest.exceptions;

public class AlreadyExistsException extends RuntimeException {
	public AlreadyExistsException() {
		super();
	}

	public AlreadyExistsException(String s) {
		super(s);
	}
}
