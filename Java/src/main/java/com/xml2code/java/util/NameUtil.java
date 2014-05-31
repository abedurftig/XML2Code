package com.xml2code.java.util;

import com.xml2code.core.definition.IMemberDefinition;

public class NameUtil {

	private static String GETTER_PREFIX = "get";
	private static String SETTER_PREFIX = "set";
	
	public static String getGetterName(IMemberDefinition memberDefinition) {
		return getGetter(memberDefinition.getName());
	}

	public static String getSetterName(IMemberDefinition memberDefinition) {
		return getSetter(memberDefinition.getName());
	}

	private static String getGetter(String name) {
		return GETTER_PREFIX + name.substring(0, 1).toUpperCase() + name.substring(1);
	}

	private static String getSetter(String name) {
		return SETTER_PREFIX + name.substring(0, 1).toUpperCase() + name.substring(1);
	}

}
