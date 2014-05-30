package com.xml2code.java.annotation;

import com.xml2code.core.definition.ListDefinition;

/**
 * Interface of an annotation which can be added to a list definition.
 */
public interface ListAnnotation {

	/**
	 * Returns the list definition the implementing annotation
	 * is related to.
	 * 
	 * @return the related list definition 
	 */
	public ListDefinition getListDefinition();

}
