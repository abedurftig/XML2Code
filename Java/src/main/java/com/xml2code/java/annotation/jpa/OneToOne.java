package com.xml2code.java.annotation.jpa;

import com.xml2code.core.definition.ReferenceDefinition;
import com.xml2code.core.util.StringConstants;
import com.xml2code.java.annotation.Annotation;
import com.xml2code.java.annotation.ReferenceAnnotation;

import org.apache.commons.lang.StringUtils;

/**
 * This annotation is added to a reference definition. In particular 
 * to a reference definition which represents a unidirectional relationship 
 * between two classes.
 */
public class OneToOne extends Annotation implements ReferenceAnnotation {

	/**
	 * The reference definition the annotation is related to.
	 */
	private ReferenceDefinition refDef;
	
	/**
	 * Constructor
	 * 
	 * @param refDef the reference definition the annotation should be related to
	 */
	public OneToOne(ReferenceDefinition refDef) {

		this.refDef = refDef;

	}
	
	/*
	 * (non-Javadoc)
	 * @see com.xml2java.app.annotations.Annotation#getAnnotationCode()
	 */
	@Override
	public String getCode() {
		
		return "@OneToOne(cascade = CascadeType.ALL)" + StringConstants.NEW_LINE +
			   "\t@JoinColumn(name=\"" + getJoinColumnName()  + "\")";
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.xml2java.app.annotations.ReferenceAnnotation#getReferenceDefinition()
	 */
	public ReferenceDefinition getReferenceDefinition() {

		return refDef;

	}
	
	/**
	 * Determines the name of the join column. It will be the name of the 
	 * referenced class transformed to upper case, followed by '_ID'.
	 * 
	 * @return the name of the join column
	 */
	private String getJoinColumnName() {
		
		// split by upper case letter
		String[] words = refDef.getType().split("(?=\\p{Upper})");
		
		for (int i = 0; i < words.length; i++) {	
			words[i] = words[i].toUpperCase();
		}
		
		return StringUtils.join(words, "_") + "_ID";
		
	}

}
