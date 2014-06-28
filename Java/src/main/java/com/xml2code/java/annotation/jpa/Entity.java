package com.xml2code.java.annotation.jpa;

import com.xml2code.core.definition.ClassDefinition;
import com.xml2code.core.util.StringConstants;
import com.xml2code.core.util.StringUtil;
import com.xml2code.java.annotation.ClassAnnotation;

/**
 * This annotation is added to a class definition. It marks a class as database backed entity
 * and specifies the table name.
 */
public class Entity extends JpaAnnotation implements ClassAnnotation {

	/** 
	 * The class definition the annotation is related to.
	 */
	private ClassDefinition classDef;
	
	/**
	 * Constructor
	 * 
	 * @param classDef the class definition the annotation should be related to
	 */
	public Entity(ClassDefinition classDef) {

		this.classDef = classDef;

	}
	
	/*
	 * (non-Javadoc)
	 * @see com.xml2java.app.annotations.Annotation#getAnnotationCode()
	 */
	@Override
	public String getCode() {
		
		String line = "";
		
		if (classDef.isSuperClass()) {
			line = "@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)";
		} else {
			line = "@Table(name = \"" + StringUtil.joinWithUnderscore(this.classDef.getClassName()) + "\")";
		}
		
		return "@Entity" + StringConstants.NEW_LINE + line;

	}
	
	/*
	 * (non-Javadoc)
	 * @see com.xml2java.app.annotations.ClassAnnotation#getClassDefinition()
	 */
	public ClassDefinition getClassDefinition() {

		return this.classDef;

	}

}
