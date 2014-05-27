package com.xml2code.java.generator;

import com.xml2code.core.definition.ProjectDefinition;
import com.xml2code.java.exception.JavaProjectCreationFailedException;

public interface IDomainClassGenerator {

	public void generateDomainObjectBaseClass(ProjectDefinition projectDef, String domainPath) throws JavaProjectCreationFailedException;
	
	public void generateDomainObjectClasses(ProjectDefinition projectDef, String domainPath) throws JavaProjectCreationFailedException;
	
}
