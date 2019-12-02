package com.bc.calendar;

public class CalendarException extends Exception {

	public CalendarException() {
		super();
	}

	public CalendarException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CalendarException(String message, Throwable cause) {
		super(message, cause);
	}

	public CalendarException(String message) {
		super(message);
	}

	public CalendarException(Throwable cause) {
		super(cause);
	}

}
