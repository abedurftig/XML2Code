package com.xml2code.core.definition;

import com.xml2code.core.types.FieldType;

/**
 * The definition of a single field.
 * 
 * @author dasnervtdoch
 */
public class FieldDefinition {

	/**
	 * The name of the field.
	 */
	private String fieldName;

	/**
	 * The type of the field.
	 */
	private FieldType fieldType;
	
	/**
	 * Whether or not the field is required
	 */
	private boolean required;
	
	
	/**
	 * Constructor
	 * 
	 * @param fieldName the name of the field
	 * @param fieldType the type of the field
	 * @param required whether or not this field is required
	 */
	public FieldDefinition(String fieldName, FieldType fieldType, boolean required) {
		
		this.fieldName = fieldName;
		this.fieldType = fieldType;
		this.required = required;
		
	}
	
	/**
	 * @return the name of the field
	 */
	public String getFieldName() {
		
		return fieldName;
		
	}

	/**
	 * @return the type of the field
	 */
	public FieldType getFieldType() {
		
		return fieldType;
		
	}

	/**
	 * @return true if the field is required; false otherwise
	 */
	public boolean isRequired() {
		
		return required;
		
	}
	
}
