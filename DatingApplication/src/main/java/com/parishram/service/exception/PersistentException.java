package com.parishram.service.exception;

public class PersistentException extends Exception {

	private static final long serialVersionUID = 1L;

	public PersistentException(String message, Throwable cause) {
		super(message, cause);

	}

	public PersistentException(String message) {
		super(message);
	}

}
