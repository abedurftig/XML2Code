package com.xml2code.core.validator;

import com.xml2code.core.definition.ProjectDefinition;
import com.xml2code.core.exception.InvalidModelException;

public interface IProjectDefinitionValidator {
	
	public void validateClassModelIntegrity(ProjectDefinition projectDef) throws InvalidModelException;
	
}
