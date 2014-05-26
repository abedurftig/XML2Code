package com.xml2code.core.validator.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.xml2code.core.definition.ClassDefinition;
import com.xml2code.core.definition.ProjectDefinition;
import com.xml2code.core.definition.ReferenceDefinition;
import com.xml2code.core.exception.InvalidModelException;
import com.xml2code.core.model.ModelFinalizer;
import com.xml2code.core.types.RelationshipType;
import com.xml2code.core.validator.IClassDefinitionValidator;

@RunWith(JUnit4.class)
public class ClassDefinitionValidatorTest {

	private IClassDefinitionValidator classVal = new ClassDefinitionValidator();
	
	@Test
	public void testInvalidRelationshipTypeManyToMany() {
		
		// arrange 
		
		ProjectDefinition projectDef = new ProjectDefinition("testInvalidRelationshipType", "");
		ClassDefinition classDefOne = new ClassDefinition(projectDef, "ClassOne", "", "");
		
		ReferenceDefinition refDefOne = new ReferenceDefinition("classTwo", "ClassTwo", false);
		refDefOne.setRelationshipType(RelationshipType.manyToMany);
		
		classDefOne.getReferenceDefinitions().add(refDefOne);
 		
		projectDef.getClassDefinitions().add(classDefOne);
		
		// act & assert 
		
		try {
			
			classVal.validateRelationships(projectDef, classDefOne);
			Assert.fail("Expected exception!");
			
		} catch (InvalidModelException e) {
			
			Assert.assertEquals("Expected different type of exception", InvalidModelException.INVALID_TYPE, e.getType());
			
		}
		
	}
	
	@Test
	public void testInvalidRelationshipTypeOneToMany() {
		
		// arrange 
		
		ProjectDefinition projectDef = new ProjectDefinition("testInvalidRelationshipType", "");
		ClassDefinition classDefOne = new ClassDefinition(projectDef, "ClassOne", "", "");
		
		ReferenceDefinition refDefOne = new ReferenceDefinition("classTwo", "ClassTwo", false);
		refDefOne.setRelationshipType(RelationshipType.oneToMany);
		
		classDefOne.getReferenceDefinitions().add(refDefOne);
 		
		projectDef.getClassDefinitions().add(classDefOne);
		
		// act & assert 
		
		try {
			
			classVal.validateRelationships(projectDef, classDefOne);
			Assert.fail("Expected exception!");
			
		} catch (InvalidModelException e) {
			
			Assert.assertEquals("Expected different type of exception", InvalidModelException.INVALID_TYPE, e.getType());
			
		}
		
	}
	
	@Test
	public void testInvalidRelationshipTwoOwners() {
		
		// arrange 
		
		ProjectDefinition projectDef = new ProjectDefinition("testInvalidRelationshipType", "");

		ClassDefinition classDefOne = new ClassDefinition(projectDef, "ClassOne", "", "");	
		ReferenceDefinition refDefOne = new ReferenceDefinition("classTwo", "ClassTwo", false);
		refDefOne.setOwner(true);
		classDefOne.getReferenceDefinitions().add(refDefOne);
		
		ClassDefinition classDefTwo = new ClassDefinition(projectDef, "ClassTwo", "", "");
		ReferenceDefinition refDefTwo = new ReferenceDefinition("classOne", "ClassOne", false);
		refDefTwo.setOwner(true);		
		classDefTwo.getReferenceDefinitions().add(refDefTwo);
		
		projectDef.getClassDefinitions().add(classDefOne);
		projectDef.getClassDefinitions().add(classDefTwo);
		
		ModelFinalizer.determineRelationships(projectDef);
		
		// act & assert 
		
		try {
			
			classVal.validateRelationships(projectDef, classDefOne);
			Assert.fail("Expected exception!");
			
		} catch (InvalidModelException e) {
			
			Assert.assertEquals("Expected different type of exception", InvalidModelException.TWO_OWNERS, e.getType());
			
		}
		
	}
	
	@Test
	public void testInvalidRelationshipNoOwner() {
		
		// arrange 
		
		ProjectDefinition projectDef = new ProjectDefinition("testInvalidRelationshipType", "");

		ClassDefinition classDefOne = new ClassDefinition(projectDef, "ClassOne", "", "");	
		ReferenceDefinition refDefOne = new ReferenceDefinition("classTwo", "ClassTwo", false);
		refDefOne.setOwner(false);
		classDefOne.getReferenceDefinitions().add(refDefOne);
		
		ClassDefinition classDefTwo = new ClassDefinition(projectDef, "ClassTwo", "", "");
		ReferenceDefinition refDefTwo = new ReferenceDefinition("classOne", "ClassOne", false);
		refDefTwo.setOwner(false);		
		classDefTwo.getReferenceDefinitions().add(refDefTwo);
		
		projectDef.getClassDefinitions().add(classDefOne);
		projectDef.getClassDefinitions().add(classDefTwo);
		
		ModelFinalizer.determineRelationships(projectDef);
		
		// act & assert 
		
		try {
			
			classVal.validateRelationships(projectDef, classDefOne);
			Assert.fail("Expected exception!");
			
		} catch (InvalidModelException e) {
			
			Assert.assertEquals("Expected different type of exception", InvalidModelException.NO_OWNER, e.getType());
			
		}
		
	}
	
}
