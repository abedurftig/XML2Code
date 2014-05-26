package com.xml2code.core.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

public class ResourceUtil {

	/**
	 * UTF-8 character encoding
	 */
	public static final String UTF_8 = "UTF-8";
	
	public static final String FILE_TYPE_JAVA = "Java";
	public static final String FILE_TYPE_XML = "XML";
	public static final String FILE_TYPE_HTML = "HTML";
	public static final String FILE_TYPE_JS = "JavaScript";
	
	/**
	 * The application logger
	 */
	private static Logger LOGGER = LoggerUtil.getApplicationLogger();
	
	/**
	 * Allows to read the content from an input stream and returns
	 * the content as String.
	 * 
	 * @param resource
	 * @return the content of the specified resource as String
	 */
	public static String readResource(InputStream resource) {
		
		StringBuffer content = new StringBuffer();
		
		Scanner sc = new Scanner(resource);
		while (sc.hasNextLine()) {
			
            content.append(sc.nextLine() + StringConstants.NEW_LINE);
            
        }
		
        sc.close();
		
        return content.toString();
		
	}
	
	/**
	 * Allows to write content to a file. After the file was written 
	 * successfully the method will log a statement using the application logger.
	 * 
	 * @param file the file the content should be written to
	 * @param content the actual content which should be written to the file
	 * @param fileType a String representing the type of file, for ex. Java, XML
	 * @throws IOException
	 */
	public static void writeContentToFile(File file, String content, String fileType) throws IOException {
		
		FileUtils.writeStringToFile(file, content, false);
		LOGGER.info("created " + fileType + " file:\t" + file.getAbsolutePath());
		
	}
	
	public static InputStream getInputStream(String relativePath) {

		return ResourceUtil.class.getResourceAsStream(relativePath);

	}

}
