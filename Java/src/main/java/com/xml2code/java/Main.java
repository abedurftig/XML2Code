package com.xml2code.java;

import com.xml2code.core.definition.ProjectDefinition;
import com.xml2code.java.exception.JavaProjectCreationFailedException;
import com.xml2code.java.factory.GeneratorFactory;
import com.xml2code.java.generator.IDomainClassGenerator;
import com.xml2code.java.generator.impl.DomainClassGenerator;

public class Main {

	public static void main(String[] args) throws JavaProjectCreationFailedException {

		ProjectDefinition projectDefinition = new ProjectDefinition("MyApp", "/tmp/XML2Code");

		String javaProjectPath = projectDefinition.getTargetDir() + "/Java";
		String srcPath = javaProjectPath + "/src/main/java/com/" + projectDefinition.getProjectName().toLowerCase();
		String domainPath = srcPath + "/domain";

		IDomainClassGenerator domainClassGenerator = GeneratorFactory.getDomainClassGenerator();

		domainClassGenerator.generateDomainObjectBaseClass(projectDefinition, domainPath);

	}

}
