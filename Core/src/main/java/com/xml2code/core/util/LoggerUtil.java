package com.xml2code.core.util;

import org.apache.log4j.Logger;

public class LoggerUtil {

	public static Logger getApplicationLogger() {
		return Logger.getLogger("XML2Code");
	}
	
	public static Logger getGeneratorLogger() {
		return Logger.getLogger("Generator"); 
	}

	public static Logger getValidatorLogger() {
		return Logger.getLogger("Validator"); 
	}

	public static Logger getFactoryLogger() {
		return Logger.getLogger("Factory"); 
	}
	
}
