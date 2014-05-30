package com.xml2code.java.util;

import com.xml2code.core.definition.FieldDefinition;
import com.xml2code.core.definition.ListDefinition;
import com.xml2code.core.definition.ReferenceDefinition;

public class NameUtil {

	private static String GETTER_PREFIX = "get";
	private static String SETTER_PREFIX = "set";

	public static String getGetterName(FieldDefinition fieldDefinition) {
		return getGetter(fieldDefinition.getFieldName());
	}

	public static String getSetterName(FieldDefinition fieldDefinition) {
		return getSetter(fieldDefinition.getFieldName());
	}

	public static String getGetterName(ReferenceDefinition referenceDefinition) {
		return getGetter(referenceDefinition.getReferenceName());
	}

	public static String getSetterName(ReferenceDefinition referenceDefinition) {
		return getSetter(referenceDefinition.getReferenceName());
	}

	public static String getGetterName(ListDefinition listDefinition) {
		return getGetter(listDefinition.getListName());
	}

	public static String getSetterName(ListDefinition listDefinition) {
		return getSetter(listDefinition.getListName());
	}

	private static String getGetter(String name) {
		return GETTER_PREFIX + name.substring(0, 1).toUpperCase() + name.substring(1);
	}

	private static String getSetter(String name) {
		return SETTER_PREFIX + name.substring(0, 1).toUpperCase() + name.substring(1);
	}

}
