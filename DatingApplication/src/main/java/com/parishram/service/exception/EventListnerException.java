package com.parishram.service.exception;

public class EventListnerException extends Exception {

	private static final long serialVersionUID = 8592818610381056414L;

	private String errorMessage;
	private Object errorObject;

	public EventListnerException() {

	}

	public EventListnerException(String exception) {
		super(exception);
	}

	public EventListnerException(String exception, Throwable cause) {
		super(exception, cause);
		this.errorMessage = exception;
		this.errorObject = cause;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Object getErrorObject() {
		return errorObject;
	}

	public void setErrorObject(Object errorObject) {
		this.errorObject = errorObject;
	}

}
