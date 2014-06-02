package com.xml2code.java.annotation.jpa;

import com.xml2code.java.annotation.Annotation;
import com.xml2code.java.annotation.ListAnnotation;
import com.xml2code.core.definition.ListDefinition;

/**
 * This annotation is added to a list definition.
 */
public class OneToMany extends Annotation implements ListAnnotation {

	/**
	 * The list definition the annotation is related to.
	 */
	private ListDefinition listDef;
	
	/**
	 * The name of the reference definition in the owned class.
	 */
	private String mappedBy;
	
	/**
	 * Constructor
	 * 
	 * @param listDef the list definition the annotation should be related to
	 * @param mappedBy the name of the reference definition in the owned class
	 */
	public OneToMany(ListDefinition listDef, String mappedBy) {

		this.listDef = listDef;
		this.mappedBy = mappedBy;

	}
	
	/*
	 * (non-Javadoc)
	 * @see com.xml2java.app.annotations.Annotation#getAnnotationCode()
	 */
	@Override
	public String getCode() {
		
		return "@OneToMany(fetch = FetchType.EAGER, mappedBy = \"" + 
			   this.mappedBy +
			   "\", cascade = CascadeType.ALL)";
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.xml2java.app.annotations.ListAnnotation#getListDefinition()
	 */
	public ListDefinition getListDefinition() {

		return this.listDef;

	}

}
