package com.xml2code.java.creator;

import org.apache.log4j.Logger;

import com.xml2code.core.definition.ProjectDefinition;
import com.xml2code.core.util.LoggerUtil;
import com.xml2code.java.exception.JavaProjectCreationFailedException;
import com.xml2code.java.factory.GeneratorFactory;
import com.xml2code.java.generator.ClassGenerator;
import com.xml2code.java.generator.IResourceFileGenerator;

public class ProjectCreator {

	protected static final Logger LOGGER = LoggerUtil.getApplicationLogger();
	
	public static void createJavaProject(ProjectDefinition projectDef) 
			throws JavaProjectCreationFailedException {

		String javaProjectPath = getJavaProjectPath(projectDef);
		String srcPath = javaProjectPath + "/src/main/java/com/" + projectDef.getProjectName().toLowerCase();
		String resourcePath = javaProjectPath + "/src/main/resources";
		
		generateDomain(projectDef, srcPath);
		generateController(projectDef, srcPath);
		generateJsonRestApi(projectDef, srcPath);
		generateResources(projectDef, resourcePath);
	}

	public static String getJavaProjectPath(ProjectDefinition projectDefinition) {
		return projectDefinition.getTargetDir() + "/" + projectDefinition.getProjectName();	
	}
	
	private static void generateDomain(ProjectDefinition projectDef, String srcPath) 
			throws JavaProjectCreationFailedException {

		LOGGER.info("--------------------------------");
		LOGGER.info("generating domain object classes");
		LOGGER.info("--------------------------------");
		
		String packagePath = srcPath + "/domain";

		ClassGenerator classGenerator = GeneratorFactory.getDomainClassGenerator();

		classGenerator.generateBaseClass(projectDef, packagePath);
		classGenerator.generateImplementationClasses(projectDef, packagePath);

	}
	
	private static void generateController(ProjectDefinition projectDef, String srcPath) 
			throws JavaProjectCreationFailedException {
		
		LOGGER.info("-----------------------------");
		LOGGER.info("generating controller classes");
		LOGGER.info("-----------------------------");
		
		String packagePath = srcPath + "/controller";
		
		ClassGenerator classGenerator = GeneratorFactory.getControllerClassGenerator();

		classGenerator.generateBaseClass(projectDef, packagePath);
		classGenerator.generateImplementationClasses(projectDef, packagePath);
		
	}
	
	private static void generateJsonRestApi(ProjectDefinition projectDef, String srcPath) 
			throws JavaProjectCreationFailedException {

		LOGGER.info("--------------------------------");
		LOGGER.info("generating JSON REST API classes");
		LOGGER.info("--------------------------------");
		
		String packagePath = srcPath + "/rest";
		
		ClassGenerator classGenerator = GeneratorFactory.getJsonRestApiGenerator();

		classGenerator.generateBaseClass(projectDef, packagePath);
		classGenerator.generateImplementationClasses(projectDef, packagePath);
		
	}
	
	private static void generateResources(ProjectDefinition projectDef, String resourcePath) 
			throws JavaProjectCreationFailedException {
		
		LOGGER.info("-----------------------------------");
		LOGGER.info("generating aditional resource files");
		LOGGER.info("-----------------------------------");
		
		IResourceFileGenerator resourceFileGenerator = GeneratorFactory.getResourceFileGenerator();
		
		resourceFileGenerator.generateResourceFiles(projectDef, resourcePath);
		
	}
	
}
