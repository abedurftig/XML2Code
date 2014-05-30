package com.xml2code.java.annotation;

import com.xml2code.core.definition.ReferenceDefinition;

/**
 * Interface of an annotation which can be added to a reference definition.
 */
public interface ReferenceAnnotation {

	/**
	 * Returns the reference definition the implementing annotation
	 * is related to.
	 * 
	 * @return the related reference definition 
	 */
	public ReferenceDefinition getReferenceDefinition();

}
