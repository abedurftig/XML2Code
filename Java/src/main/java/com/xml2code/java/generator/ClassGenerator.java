package com.xml2code.java.generator;

import java.io.IOException;
import java.util.List;

import com.xml2code.core.definition.ClassDefinition;
import com.xml2code.core.definition.ProjectDefinition;
import com.xml2code.core.generator.FileGenerator;
import com.xml2code.core.generator.ReplacementInstruction;
import com.xml2code.core.util.ResourceUtil;
import com.xml2code.java.exception.JavaProjectCreationFailedException;

public abstract class ClassGenerator extends FileGenerator {
	
	public abstract void generateBaseClass(ProjectDefinition projectDefinition, String targetDir) 
			throws JavaProjectCreationFailedException;
	
	public void generateImplementationClasses(ProjectDefinition projectDefinition, String targetDir) 
			throws JavaProjectCreationFailedException {
		
		String rootPackage = getRootPackage(projectDefinition);
		
		for (ClassDefinition classDefinition : projectDefinition.getClassDefinitions()) {
			
			generateImplementationClass(classDefinition, targetDir, rootPackage);
			
		}
		
	}
	
	protected abstract void generateImplementationClass(ClassDefinition classDefinition, String targetDir, String rootPackage) 
			throws JavaProjectCreationFailedException;
	
	protected void writeOutFile(String template, List<ReplacementInstruction> replacementInstructions,
			String contentFilePath) throws JavaProjectCreationFailedException {
		
		try {
			
			writeOutFile(template, replacementInstructions, contentFilePath, ResourceUtil.FILE_TYPE_JAVA);
			
		} catch (IOException e) {
			
			throw new JavaProjectCreationFailedException(e);
			
		}
		
	}
	
	public static String getRootPackage(ProjectDefinition projectDefinition) {

		return "com." + projectDefinition.getProjectName().toLowerCase();

	}
	
}
