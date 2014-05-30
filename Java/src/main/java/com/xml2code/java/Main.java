package com.xml2code.java;

import com.xml2code.core.definition.ClassDefinition;
import com.xml2code.core.definition.ProjectDefinition;
import com.xml2code.core.model.ModelFinalizer;
import com.xml2code.java.exception.JavaProjectCreationFailedException;
import com.xml2code.java.factory.GeneratorFactory;
import com.xml2code.java.generator.IDomainClassGenerator;

public class Main {

	public static void main(String[] args) throws JavaProjectCreationFailedException {

		ProjectDefinition projectDefinition = new ProjectDefinition("MyApp", "/Applications/Development/tmp/XML2Code");

		ClassDefinition contact = new ClassDefinition(projectDefinition, "Contact", "", "This is the contact class");
		ClassDefinition person = new ClassDefinition(projectDefinition, "Person", "Contact", "This is the person class");

		projectDefinition.getClassDefinitions().add(contact);
		projectDefinition.getClassDefinitions().add(person);

		String javaProjectPath = projectDefinition.getTargetDir() + "/Java";
		String srcPath = javaProjectPath + "/src/main/java/com/" + projectDefinition.getProjectName().toLowerCase();
		String domainPath = srcPath + "/domain";

		ModelFinalizer.finalize(projectDefinition);

		IDomainClassGenerator domainClassGenerator = GeneratorFactory.getDomainClassGenerator();

		domainClassGenerator.generateDomainObjectBaseClass(projectDefinition, domainPath);
		domainClassGenerator.generateDomainObjectClasses(projectDefinition, domainPath);

	}

}
