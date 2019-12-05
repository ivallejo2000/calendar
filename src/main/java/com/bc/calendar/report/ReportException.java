package com.bc.calendar.report;

@SuppressWarnings("serial")
public class ReportException extends Exception {

	public ReportException() {
		super();
	}

	public ReportException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ReportException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReportException(String message) {
		super(message);
	}

	public ReportException(Throwable cause) {
		super(cause);
	}

}
