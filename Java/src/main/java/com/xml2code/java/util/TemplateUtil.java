package com.xml2code.java.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.xml2code.core.util.ResourceUtil;

public final class TemplateUtil {

	private static final String JAVA_PATH = "/java-templates/";
	private static final String JAVA_P_PATH = "/java-partial-templates/";
	
	private static final String JAVA_DOMAIN_OBJECT = 			JAVA_PATH + "DomainObject.java.template";
	private static final String JAVA_DOMAIN_OBJECT_IMPL = 		JAVA_PATH + "DomainObjectImpl.java.template";

	private static final String JAVA_DOMAIN_OBJECT_IMPL_FIELD = JAVA_P_PATH + "field.partial.template";

	// ------------------------------------------
	// Template cache
	// ------------------------------------------
	
	private static Map<String, String> templates = new HashMap<String, String>();
	
	// ------------------------------------------
	// Java templates
	// ------------------------------------------
	
	/**
	 * @return the template for the domain object base class
	 */
	public static String getJavaDomainObjectTemplate() {
		return getTemplate(JAVA_DOMAIN_OBJECT);
	}

	/**
	 * @return the template for a domain object implementation class
	 */
	public static String getJavaDomainObjectImplTemplate() {
		return getTemplate(JAVA_DOMAIN_OBJECT_IMPL);
	}

	// ------------------------------------------
	// Java partial templates
	// ------------------------------------------

	/**
	 * @return the template for domain object class field
	 */
	public static String getJavaPartialFieldTemplate() {
		return getTemplate(JAVA_DOMAIN_OBJECT_IMPL_FIELD);
	}

	// ------------------------------------------
	// Private methods
	// ------------------------------------------
	
	private static String getTemplate(String resourcePath) {
		
		if (templates.containsKey(resourcePath)) {
			
			return templates.get(resourcePath);
			
		} else {
			
			InputStream templateStream = TemplateUtil.class.getResourceAsStream(resourcePath);
			String template = ResourceUtil.readResource(templateStream);
			templates.put(resourcePath, template);
			
			return template;
			
		}
		
	}
	
}
