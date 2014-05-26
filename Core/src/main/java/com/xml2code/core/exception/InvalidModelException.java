package com.xml2code.core.exception;

public class InvalidModelException extends Exception {

	public static final int INVALID_TYPE = 1;
	public static final int NO_LIST = 2;
	public static final int NO_OWNER = 3;
	public static final int TWO_OWNERS = 4;
	public static final int NO_BACK_REF = 5;
	public static final int INVALID_BACK_REF = 6;
	
	public static final int MISSING_DEPENDENCY = 7;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int type = -1;
	
	public InvalidModelException(String message) {
		
		super(message);
		
	}
	
	public InvalidModelException(int type, String message) {
		
		super(message);
		this.type = type;
		
	}
	
	public int getType() {
		return this.type;
	}

}
