package com.xml2code.java.annotation.jpa;

import com.xml2code.core.definition.FieldDefinition;
import com.xml2code.core.types.FieldType;
import com.xml2code.java.annotation.FieldAnnotation;
import org.apache.commons.lang.StringUtils;

/**
 * This annotation is added to a field definition. It defines the basic properties
 * of a database column such as name, unique, null-able, length.
 */
public class Column extends JpaAnnotation implements FieldAnnotation {

	/**
	 * The field definition the annotation is related to.
	 */
	private FieldDefinition fieldDef;
	
	/**
	 * Constructor
	 * 
	 * @param fieldDef the field definition the annotation should be related to
	 */
	public Column(FieldDefinition fieldDef) {

		this.fieldDef = fieldDef;

	}
	
	/*
	 * (non-Javadoc)
	 * @see com.xml2java.app.annotations.Annotation#getAnnotationCode()
	 */
	public String getCode() {
		
		String code = "@Column(" +
					   "name = \"" + getColumnName() + "\", " +
					   "unique = false, " +
					   "nullable = " +  !this.fieldDef.isRequired();
		
		if (this.fieldDef.getFieldType().equals(FieldType.shorttext)) {

			code += ", length = 255";

		} else if (this.fieldDef.getFieldType().equals(FieldType.text)) {

			code += ", length = 10000";

		}
		
		code += ")";
		
		return code;

	}
	
	/*
	 * (non-Javadoc)
	 * @see com.xml2java.app.annotations.FieldAnnotation#getFieldDefinition()
	 */
	public FieldDefinition getFieldDefinition() {

		return this.fieldDef;

	}
	
	/**
	 * Determines the name of the column storing the value of the related field. 
	 * The field name is split at every capital letter. All parts are then transformed 
	 * to upper case and joined with an underscore character.
	 * 
	 * @return the name of the database table column
	 */
	private String getColumnName() {
		
		// split by upper case letter
		String[] words = this.fieldDef.getName().split("(?=\\p{Upper})");
		
		for (int i = 0; i < words.length; i++) {	
			words[i] = words[i].toUpperCase();
		}
		
		return StringUtils.join(words, "_");
		
	}

}
