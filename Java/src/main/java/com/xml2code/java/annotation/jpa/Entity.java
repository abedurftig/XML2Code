package com.xml2code.java.annotation.jpa;

import com.xml2code.core.definition.ClassDefinition;
import com.xml2code.core.util.StringConstants;
import com.xml2code.java.annotation.ClassAnnotation;
import org.apache.commons.lang.StringUtils;

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
	public String getAnnotationCode() {
		
		String line = "";
		
		if (classDef.isSuperClass()) {
			line = "@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)";
		} else {
			line = "@Table(name = \"" + getEntityName() + "\")";
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
	
	/**
	 * Determines the name of the database table backing an entity.
	 * The class name is split at every capital letter. All parts are then transformed 
	 * to upper case and joined with an underscore character.
	 * 
	 * @return the name of the database table
	 */
	private String getEntityName() {
		
		// split by upper case letter
		String[] words = classDef.getClassName().split("(?=\\p{Upper})");
		
		for (int i = 0; i < words.length; i++) {	
			words[i] = words[i].toUpperCase();
		}
		
		return StringUtils.join(words, "_");
		
	}

}
