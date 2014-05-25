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
	
	private boolean editable;
	
	
	/**
	 * Constructor
	 * 
	 * @param fieldName the name of the field
	 * @param fieldType the type of the field
	 * @param required whether or not this field is required
	 */
	public FieldDefinition(String fieldName, FieldType fieldType, boolean required) {
		
		this(fieldName, fieldType, required, false);
		
	}
	
	/**
	 * Constructor
	 * 
	 * @param fieldName the name of the field
	 * @param fieldType the type of the field
	 * @param required whether or not this field is required
	 * @param editable whether or not this field is editable
	 */
	public FieldDefinition(String fieldName, FieldType fieldType, boolean required, boolean editable) {
		
		this.fieldName = fieldName;
		this.fieldType = fieldType;
		this.required = required;
		this.editable = editable;
		
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

	/**
	 * @return the editable
	 */
	public boolean isEditable() {
		return editable;
	}

	/**
	 * @param editable the editable to set
	 */
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	
}
