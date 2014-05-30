package com.xml2code.java.annotation;

/**
 * The base class for all annotations to be created and added to a member
 * of generated class or the generated class itself.
 */
public abstract class Annotation {
	
	/**
	 * Returns the annotation code which should be 
	 * added to the generated Java code.
	 * 
	 * @return the annotation code
	 */
	public abstract String getAnnotationCode();
	
}
