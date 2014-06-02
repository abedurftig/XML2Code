package com.xml2code.java.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.xml2code.core.util.ResourceUtil;

public final class TemplateUtil {

	private static final String JAVA_PATH = "/java-templates/";
	private static final String JAVA_P_PATH = "/java-partial-templates/";
	private static final String RESRC_XML_PATH = "/resource-xml/";
	
	private static final String JAVA_DOMAIN_OBJECT = 	  JAVA_PATH + "DomainObject.java.template";
	private static final String JAVA_DOMAIN_OBJECT_IMPL = JAVA_PATH + "DomainObjectImpl.java.template";
	private static final String JAVA_CONTROLLER = 	  	  JAVA_PATH + "Controller.java.template";
	private static final String JAVA_CONTROLLER_IMPL = 	  JAVA_PATH + "ControllerImpl.java.template";
	private static final String JAVA_JSON_REST_API = 	  JAVA_PATH + "JsonRestApi.java.template";
	private static final String JAVA_JSON_REST_API_IMPL = JAVA_PATH + "JsonRestApiImpl.java.template";
	
	private static final String JAVA_DOMAIN_OBJECT_IMPL_FIELD = 		JAVA_P_PATH + "field.partial.template";
	private static final String JAVA_DOMAIN_OBJECT_IMPL_REFERENCE = 	JAVA_P_PATH + "reference.partial.template";
	private static final String JAVA_DOMAIN_OBJECT_IMPL_LIST = 			JAVA_P_PATH + "list.partial.template";
	private static final String JAVA_DOMAIN_OBJECT_IMPL_GETTER_SETTER = JAVA_P_PATH + "gettersetter.partial.template";
	private static final String JAVA_DOMAIN_OBJECT_IMPL_INIT_LIST = 	JAVA_P_PATH + "initlist.partial.template";
	private static final String JAVA_DOMAIN_OBJECT_IMPL_CONSTRUCTOR = 	JAVA_P_PATH + "constructor.partial.template";

	private static final String RESRC_WEB_XML = 		  RESRC_XML_PATH + "web.xml.resrc";
	private static final String RESRC_HIBERNATE_CFG_XML = RESRC_XML_PATH + "hibernate.cfg.xml.resrc";
	private static final String RESRC_POM_XML = 		  RESRC_XML_PATH + "pom.xml.resrc";
	
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
	
	/**
	 * @return the template for the controller base class
	 */
	public static String getJavaControllerTemplate() {
		return getTemplate(JAVA_CONTROLLER);
	}
	
	/**
	 * @return the template for the controller implementation class
	 */
	public static String getJavaControllerImplTemplate() {
		return getTemplate(JAVA_CONTROLLER_IMPL);
	}
	
	/**
	 * @return the template for the JSON REST API base class
	 */
	public static String getJavaJsonRestApiTemplate() {
		return getTemplate(JAVA_JSON_REST_API);
	}
	
	/**
	 * @return the template for the JSON REST API implementation class
	 */
	public static String getJavaJsonRestApiImplTemplate() {
		return getTemplate(JAVA_JSON_REST_API_IMPL);
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

	/**
	 * @return the template for domain object class reference
	 */
	public static String getJavaPartialReferenceTemplate() {
		return getTemplate(JAVA_DOMAIN_OBJECT_IMPL_REFERENCE);
	}
	
	/**
	 * @return the template for domain object class list
	 */
	public static String getJavaPartialListTemplate() {
		return getTemplate(JAVA_DOMAIN_OBJECT_IMPL_LIST);
	}

	/**
	 * @return the template for domain object class getter / setter
	 */
	public static String getJavaPartialGetterSetterTemplate() {
		return getTemplate(JAVA_DOMAIN_OBJECT_IMPL_GETTER_SETTER);
	}
	
	public static String getJavaPartialInitListTemplate() {
		return getTemplate(JAVA_DOMAIN_OBJECT_IMPL_INIT_LIST);
	}

	public static String getJavaPartialConstructorTemplate() {
		return getTemplate(JAVA_DOMAIN_OBJECT_IMPL_CONSTRUCTOR);
	}

	// ------------------------------------------
	// XML templates
	// ------------------------------------------
	
	public static String getXMLResourceWebXML() {
		return getTemplate(RESRC_WEB_XML);
	}
	
	public static String getXMLResourcePomXML() {
		return getTemplate(RESRC_POM_XML);
	}
	
	public static String getXMLResourceHibernateCfgXML() {
		return getTemplate(RESRC_HIBERNATE_CFG_XML);
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
