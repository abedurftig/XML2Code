package com.xml2code.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 
 * @author dasnervtdoch
 *
 */
public class PropertiesUtil {
	
	protected static Logger LOGGER = LoggerUtil.getApplicationLogger();
	
	private static final String OS = System.getProperty("os.name").toLowerCase();
	
	protected static Properties applicationProperties = null;
	
	private static final String PROJECT_FILE_NAME_KEY = "project.file.name";
	
	/**
	 * Gets the application properties. Loads them if not yet loaded.
	 * 
	 * @return the application properties
	 */
	protected static Properties getApplicationProperties() {
		
		if (applicationProperties == null) {
			
			applicationProperties = new Properties();
			loadApplicationProperties();
		}
		
		return applicationProperties;
		
	}
	
	/**
	 * Actually loading the application properties.
	 */
	protected static void loadApplicationProperties() {
		
		InputStream input = null;
		
		try {
			 
			input = PropertiesUtil.class.getResourceAsStream("/application.properties");
	 		applicationProperties.load(input);
	 
		} catch (IOException ex) {
			
			LOGGER.error("Could not load 'application.properties'", ex);
			
		} finally {
			
			if (input != null) {
				
				try {
					
					input.close();
					
				} catch (IOException e) {
					
					LOGGER.error("Could not close input stream", e);
					
				}
			}
		}
	}
	
	/**
	 * Prefixes the key with either 'win', 'mac', 'unix' or nothing 
	 * depending on the operating system.
	 * 
	 * @param propertyName
	 * @return a platform aware key
	 */
	protected static String getPlatformAwareKey(String propertyName) {
		
		if (isWindows()) {
			return "win." + propertyName;
		} else if (isMac()) {
			return "mac." + propertyName;
		} else if (isUnix()) {
			return "unix." + propertyName;
		} else {
			return propertyName;
		}
		
	}
	
	/**
	 * @return true if the operating system is Windows
	 */
	public static boolean isWindows() {
		return (OS.indexOf("win") >= 0);
	}
 
	/**
	 * @return true if the operating system is MacOS
	 */
	public static boolean isMac() {
		return (OS.indexOf("mac") >= 0);
	}
 
	/**
	 * @return true if the operating system is Unix
	 */
	public static boolean isUnix() {
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
	}
	
	public static String getProjectFileName() {
		return getApplicationProperties().getProperty(PROJECT_FILE_NAME_KEY);
	}
	
}
