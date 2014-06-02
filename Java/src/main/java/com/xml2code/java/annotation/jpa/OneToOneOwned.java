package com.xml2code.java.annotation.jpa;

import com.xml2code.core.definition.ReferenceDefinition;
import com.xml2code.java.annotation.Annotation;
import com.xml2code.java.annotation.ReferenceAnnotation;

/**
 * This annotation is added to a reference definition. In particular 
 * to a reference definition which represents a bidirectional relationship 
 * between two classes where the class containing the reference definition is 
 * the considered the owned class.
 */
public class OneToOneOwned extends Annotation implements ReferenceAnnotation {

	/**
	 * The reference definition the annotation is related to.
	 */
	private ReferenceDefinition refDef;
	
	/**
	 * Constructor
	 * 
	 * @param refDef the reference definition the annotation should be related to
	 */
	public OneToOneOwned(ReferenceDefinition refDef) {

		this.refDef = refDef;

	}
	
	/*
	 * (non-Javadoc)
	 * @see com.xml2java.app.annotations.Annotation#getAnnotationCode()
	 */
	public String getCode() {

		return "@OneToOne(fetch = FetchType.LAZY)";

	}
	
	/*
	 * (non-Javadoc)
	 * @see com.xml2java.app.annotations.ReferenceAnnotation#getReferenceDefinition()
	 */
	public ReferenceDefinition getReferenceDefinition() {

		return refDef;

	}

}
