package com.xml2code.core.validator;

import com.xml2code.core.definition.ClassDefinition;
import com.xml2code.core.definition.ProjectDefinition;
import com.xml2code.core.exception.InvalidModelException;

public interface IClassDefinitionValidator {
	
	public void validateRelationships(ProjectDefinition projectDef, ClassDefinition classDef) throws InvalidModelException;
	
}
