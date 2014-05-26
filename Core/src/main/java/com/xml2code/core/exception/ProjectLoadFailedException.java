package com.xml2code.core.exception;

public class ProjectLoadFailedException extends Exception {

	public static final String NO_PROJECT = "Project definition does not exist!";
	public static final String NO_CLASS_DEF_DIRECTORY = "Directory 'classes' does not exist!";
	public static final String NO_CLASS_DEF = "The project does not contain any class definitions!";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Exception rootCause;

	public ProjectLoadFailedException(String message) {
		super(message);
	}
	
	public ProjectLoadFailedException(Exception rootCause) {
		super("The project load failed.");
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
