package com.xml2code.core.factory;

import com.xml2code.core.validator.IClassDefinitionValidator;
import com.xml2code.core.validator.IClassXMLValidator;
import com.xml2code.core.validator.IProjectDefinitionValidator;
import com.xml2code.core.validator.IProjectXMLValidator;
import com.xml2code.core.validator.impl.ClassDefinitionValidator;
import com.xml2code.core.validator.impl.ClassXMLValidator;
import com.xml2code.core.validator.impl.ProjectDefinitionValidator;
import com.xml2code.core.validator.impl.ProjectXMLValidator;

public class ValidatorFactory {

	private ValidatorFactory() {}
	
	public static IProjectXMLValidator getProjectXMLValidator() {
		return new ProjectXMLValidator();
	}
	
	public static IProjectDefinitionValidator getProjectDefValidator() {
		return new ProjectDefinitionValidator();
	}
	
	public static IClassXMLValidator getClassXMLValidator() {
		return new ClassXMLValidator();
	}
	
	public static IClassDefinitionValidator getClassDefValidator() {
		return new ClassDefinitionValidator();
	}

}
