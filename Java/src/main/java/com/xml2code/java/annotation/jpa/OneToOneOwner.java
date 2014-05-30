package com.xml2code.java.annotation.jpa;

import com.xml2code.core.definition.ReferenceDefinition;
import com.xml2code.java.annotation.Annotation;
import com.xml2code.java.annotation.ReferenceAnnotation;

/**
 * This annotation is added to a reference definition. In particular 
 * to a reference definition which represents a bidirectional relationship 
 * between two classes where the class containing the reference definition is 
 * the owner of the relationship.
 */
public class OneToOneOwner extends Annotation implements ReferenceAnnotation {

	/**
	 * The reference definition the annotation is related to.
	 */
	private ReferenceDefinition refDef;
	
	/**
	 * The name of the reference definition in the owned class.
	 */
	private String mappedBy;
	
	/**
	 * Constructor
	 * 
	 * @param refDef the reference definition the annotation should be related to
	 * @param mappedBy the name of the reference definition in the owned class
	 */
	public OneToOneOwner(ReferenceDefinition refDef, String mappedBy) {

		this.refDef = refDef;
		this.mappedBy = mappedBy;

	}
	
	/*
	 * (non-Javadoc)
	 * @see com.xml2java.app.annotations.Annotation#getAnnotationCode()
	 */
	public String getAnnotationCode() {
		
		return "@OneToOne(fetch = FetchType.LAZY, " +
			   "mappedBy = \"" + this.mappedBy + "\", " +
			   "cascade = CascadeType.ALL)";
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.xml2java.app.annotations.ReferenceAnnotation#getReferenceDefinition()
	 */
	public ReferenceDefinition getReferenceDefinition() {

		return this.refDef;

	}

}
