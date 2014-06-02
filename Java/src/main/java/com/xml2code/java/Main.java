package com.xml2code.java;

import com.xml2code.core.definition.ClassDefinition;
import com.xml2code.core.definition.FieldDefinition;
import com.xml2code.core.definition.ListDefinition;
import com.xml2code.core.definition.ProjectDefinition;
import com.xml2code.core.definition.ReferenceDefinition;
import com.xml2code.core.model.ModelFinalizer;
import com.xml2code.core.types.FieldType;
import com.xml2code.java.exception.JavaProjectCreationFailedException;
import com.xml2code.java.factory.GeneratorFactory;
import com.xml2code.java.generator.IDomainClassGenerator;

public class Main {

	public static void main(String[] args) throws JavaProjectCreationFailedException {

		ProjectDefinition projectDefinition = new ProjectDefinition("MyApp", "/Applications/Development/tmp/XML2Code");

		ListDefinition addresses = new ListDefinition("addresses", "Address");
		FieldDefinition firstName = new FieldDefinition("firstName", FieldType.shorttext, true, true);
		
		FieldDefinition aDate = new FieldDefinition("aDate", FieldType.date, false);
		FieldDefinition aDateTime = new FieldDefinition("aDateTime", FieldType.datetime, false);
		
		ReferenceDefinition jobRef = new ReferenceDefinition("job", "Job", false);
		jobRef.setOwner(true);
		ReferenceDefinition contactRef = new ReferenceDefinition("contact", "Contact", true);
		contactRef.setOwner(false);
		
		ClassDefinition contact = new ClassDefinition(projectDefinition, "Contact", "", "This is the contact class");
		ClassDefinition address = new ClassDefinition(projectDefinition, "Address", "", "This is the address class");
		ClassDefinition person = new ClassDefinition(projectDefinition, "Person", "Contact", "This is the person class");
		ClassDefinition job = new ClassDefinition(projectDefinition, "Job", "", "This is the job class");
		
		person.getReferenceDefinitions().add(jobRef);
		person.getFieldDefinitions().add(firstName);
		person.getFieldDefinitions().add(aDate);
		person.getFieldDefinitions().add(aDateTime);
		
		contact.getListDefinitions().add(addresses);
		
		address.getReferenceDefinitions().add(contactRef);
		
		projectDefinition.getClassDefinitions().add(contact);
		projectDefinition.getClassDefinitions().add(person);
		projectDefinition.getClassDefinitions().add(address);
		projectDefinition.getClassDefinitions().add(job);
		
		String javaProjectPath = projectDefinition.getTargetDir() + "/Java";
		String srcPath = javaProjectPath + "/src/main/java/com/" + projectDefinition.getProjectName().toLowerCase();
		String domainPath = srcPath + "/domain";

		ModelFinalizer.finalize(projectDefinition);

		IDomainClassGenerator domainClassGenerator = GeneratorFactory.getDomainClassGenerator();

		domainClassGenerator.generateDomainObjectBaseClass(projectDefinition, domainPath);
		domainClassGenerator.generateDomainObjectClasses(projectDefinition, domainPath);

	}

}
