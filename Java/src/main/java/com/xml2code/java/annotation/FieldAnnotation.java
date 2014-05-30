package com.xml2code.java.annotation;

import com.xml2code.core.definition.FieldDefinition;

/**
 * Interface of an annotation which can be added to a field definition.
 */
public interface FieldAnnotation {

	/**
	 * Returns the field definition the implementing annotation
	 * is related to.
	 * 
	 * @return the related field definition 
	 */
	public FieldDefinition getFieldDefinition();

}
