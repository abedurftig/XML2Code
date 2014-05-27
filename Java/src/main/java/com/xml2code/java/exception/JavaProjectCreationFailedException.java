package com.xml2code.java.exception;

public class JavaProjectCreationFailedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Exception rootCause;

	public JavaProjectCreationFailedException(String message) {
		super(message);
	}

	public JavaProjectCreationFailedException(Exception rootCause) {
		super("The project creation failed.");
		this.rootCause = rootCause;
	}

	public String getMessage() {

		if (rootCause != null) {

			return super.getMessage() +
					" The root cause is a "
					+ rootCause.getClass() + ": "
					+ rootCause.getMessage();

		} else {

			return super.getMessage();

		}

	}
	
}
