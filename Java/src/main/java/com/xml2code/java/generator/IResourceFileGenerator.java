package com.xml2code.java.generator;

import com.xml2code.core.definition.ProjectDefinition;
import com.xml2code.java.exception.JavaProjectCreationFailedException;

public interface IResourceFileGenerator {

	public void generateResourceFiles(ProjectDefinition projectDefinition, String targetDir) 
			throws JavaProjectCreationFailedException;
	
}
