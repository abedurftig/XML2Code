package com.xml2code.java.annotation;

import com.xml2code.core.definition.ClassDefinition;

/**
 * Interface of an annotation which can be added to a class definition.
 */
public interface ClassAnnotation {

	/**
	 * Returns the class definition the implementing annotation
	 * is related to.
	 * 
	 * @return the related class definition 
	 */
	public ClassDefinition getClassDefinition();

}
