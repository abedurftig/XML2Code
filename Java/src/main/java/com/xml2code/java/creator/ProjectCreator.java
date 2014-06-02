package com.xml2code.java.creator;

import org.apache.log4j.Logger;

import com.xml2code.core.definition.ProjectDefinition;
import com.xml2code.core.util.LoggerUtil;
import com.xml2code.java.exception.JavaProjectCreationFailedException;
import com.xml2code.java.factory.GeneratorFactory;
import com.xml2code.java.generator.ClassGenerator;

public class ProjectCreator {

	protected static Logger LOGGER = LoggerUtil.getApplicationLogger();
	
	public static void createJavaProject(ProjectDefinition projectDef) throws JavaProjectCreationFailedException {

		String javaProjectPath = projectDef.getTargetDir() + "/Java";
		String srcPath = javaProjectPath + "/src/main/java/com/" + projectDef.getProjectName().toLowerCase();

		LoggerUtil.getApplicationLogger().info("--------------------------------");
		LoggerUtil.getApplicationLogger().info("generating domain object classes");
		LoggerUtil.getApplicationLogger().info("--------------------------------");
		generateDomain(projectDef, srcPath);
		
		LoggerUtil.getApplicationLogger().info("-----------------------------");
		LoggerUtil.getApplicationLogger().info("generating controller classes");
		LoggerUtil.getApplicationLogger().info("-----------------------------");
		generateController(projectDef, srcPath);

		LoggerUtil.getApplicationLogger().info("--------------------------------");
		LoggerUtil.getApplicationLogger().info("generating JSON REST API classes");
		LoggerUtil.getApplicationLogger().info("--------------------------------");
		generateJsonRestApi(projectDef, srcPath);
		
	}

	private static void generateDomain(ProjectDefinition projectDef, String srcPath) throws JavaProjectCreationFailedException {

		String packagePath = srcPath + "/domain";

		ClassGenerator classGenerator = GeneratorFactory.getDomainClassGenerator();

		classGenerator.generateBaseClass(projectDef, packagePath);
		classGenerator.generateImplementationClasses(projectDef, packagePath);

	}
	
	private static void generateController(ProjectDefinition projectDef, String srcPath) throws JavaProjectCreationFailedException {
		
		String packagePath = srcPath + "/controller";
		
		ClassGenerator classGenerator = GeneratorFactory.getDomainClassGenerator();

		classGenerator.generateBaseClass(projectDef, packagePath);
		classGenerator.generateImplementationClasses(projectDef, packagePath);
		
	}
	
	private static void generateJsonRestApi(ProjectDefinition projectDef, String srcPath) throws JavaProjectCreationFailedException {
		
		String packagePath = srcPath + "/rest";
		
		ClassGenerator classGenerator = GeneratorFactory.getDomainClassGenerator();

		classGenerator.generateBaseClass(projectDef, packagePath);
		classGenerator.generateImplementationClasses(projectDef, packagePath);
		
	}
	
}
