package util;

import com.xml2code.core.util.PropertiesUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestPropertiesUtil extends PropertiesUtil {

	protected static Properties testProperties = null;
	
	protected static Properties getTestProperties() {
		
		if (testProperties == null) {

			testProperties = new Properties();
			loadTestProperties();

		}
		
		return testProperties;
	}
	
	protected static void loadTestProperties() {
		
		InputStream input = null;
		
		try {
			
			input = TestPropertiesUtil.class.getResourceAsStream("/test.properties");
			testProperties.load(input);
	 
		} 
		catch (IOException ex) {
			
			LOGGER.error("Could not load 'test.properties'", ex);
			
		} 
		finally {
			
			if (input != null) {
				
				try {

					input.close();

				} 
				catch (IOException e) {
					
					LOGGER.error("Could not close input stream", e);
					
				}

			}

		}

	}
	
	// Properties
	
	public static String getTestOutputDirectory() {

		return getTestProperties().getProperty(getPlatformAwareKey("junit.output.dir"));

	}

}
