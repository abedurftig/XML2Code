package com.xml2code.java.annotation.jackson;

import com.xml2code.core.definition.ReferenceDefinition;
import com.xml2code.java.annotation.ReferenceAnnotation;

/**
 *
 */
public class JsonIgnore extends JacksonAnnotation implements ReferenceAnnotation {

	/**
	 * The reference definition this annotation is related to.
	 */
	private ReferenceDefinition refDef;
	
	public JsonIgnore(ReferenceDefinition refDef) {

		this.refDef = refDef;

	}
	
	/* (non-Javadoc)
	 * @see com.xml2java.app.annotation.ReferenceAnnotation#getReferenceDefinition()
	 */
	public ReferenceDefinition getReferenceDefinition() {
		return this.refDef;
	}

	/*
	 * (non-Javadoc)
	 * @see com.xml2java.app.annotation.Annotation#getAnnotationCode()
	 */
	public String getAnnotationCode() {

		return "@JsonIgnore";

	}

}
