package com.parishram.service.exception;

public class ServiceException extends Exception {

	private static final long serialVersionUID = 5710941125914534919L;

	public ServiceException(String exception) {
		super(exception);
	}

	public ServiceException(String exception, Throwable cause) {
		super(exception, cause);
	}

}
