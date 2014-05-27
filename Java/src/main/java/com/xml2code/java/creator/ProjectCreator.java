package com.xml2code.java.creator;

import com.xml2code.core.definition.ClassDefinition;
import com.xml2code.java.factory.GeneratorFactory;
import com.xml2code.java.generator.IDomainClassGenerator;
import org.apache.log4j.Logger;

import com.xml2code.core.definition.ProjectDefinition;
import com.xml2code.core.util.LoggerUtil;
import com.xml2code.java.exception.JavaProjectCreationFailedException;

public class ProjectCreator {

	protected static Logger LOGGER = LoggerUtil.getApplicationLogger();
	
	public static void createJavaProject(ProjectDefinition projectDef) throws JavaProjectCreationFailedException {

		String javaProjectPath = projectDef.getTargetDir() + "/Java";
		String srcPath = javaProjectPath + "/src/main/java/com/" + projectDef.getProjectName().toLowerCase();

		generateDomain(projectDef, srcPath);

	}

	private static void generateDomain(ProjectDefinition projectDef, String srcPath) throws JavaProjectCreationFailedException {

		String domainPath = srcPath + "/domain";

		IDomainClassGenerator domainClassGenerator = GeneratorFactory.getDomainClassGenerator();

		domainClassGenerator.generateDomainObjectBaseClass(projectDef, domainPath);
		domainClassGenerator.generateDomainObjectClasses(projectDef, domainPath);

	}
	
}
