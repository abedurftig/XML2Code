package com.xml2code.core.validator.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.xml2code.core.definition.ClassDefinition;
import com.xml2code.core.definition.ListDefinition;
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
	public void testReferenceRelationshipTypeManyToMany() {
		
		// arrange 
		
		ProjectDefinition projectDef = new ProjectDefinition("testInvalidRelationshipType", "");
		ClassDefinition classDefOne = new ClassDefinition(projectDef, "ClassOne", "", "");
		
		ReferenceDefinition refDefOne = new ReferenceDefinition("classTwo", "ClassTwo", false);
		refDefOne.setRelationshipType(RelationshipType.manyToMany);
		
		classDefOne.getReferenceDefinitions().add(refDefOne);
 		
		projectDef.getClassDefinitions().add(classDefOne);
		
		// act & assert 
		
		/*
		 * This is a one to one relationship, but the reference has a relationship type
		 * of many to many, so we expect the validation to fail.
		 */
		
		try {
			
			classVal.validateRelationships(projectDef, classDefOne);
			Assert.fail("Expected exception!");
			
		} catch (InvalidModelException e) {
			
			Assert.assertEquals("Expected different type of exception", InvalidModelException.INVALID_TYPE, e.getType());
			
		}
		
	}
	
	@Test
	public void testReferenceRelationshipTypeOneToMany() {
		
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
	public void testReferenceRelationshipTwoOwners() {
		
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
	public void testReferenceRelationshipNoOwner() {
		
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
	
	@Test
	public void testListRelationshipManyToManyNoOwner() {
		
		// arrange 
		
		ProjectDefinition projectDef = new ProjectDefinition("School", "");
		
		ListDefinition stundents = new ListDefinition("students", "Student");
		stundents.setRelationshipType(RelationshipType.manyToMany);
		ListDefinition courses = new ListDefinition("courses", "Course");
		courses.setRelationshipType(RelationshipType.manyToMany);
		
		ClassDefinition course = new ClassDefinition(projectDef, "Course", "", "");
		course.getListDefinitions().add(stundents);
		
		ClassDefinition student = new ClassDefinition(projectDef, "Student", "", "");
 		student.getListDefinitions().add(courses);
		
 		projectDef.getClassDefinitions().add(student);
 		projectDef.getClassDefinitions().add(course);
 		
		// act & assert 
		
		/*
		 * This is a many to many relationship between course and student, 
		 * but no side is the owner, so we expect the validation to fail.
		 */
		
		try {
			
			classVal.validateRelationships(projectDef, student);
			classVal.validateRelationships(projectDef, course);
			Assert.fail("Expected exception!");
			
		} catch (InvalidModelException e) {
			
			Assert.assertEquals("Expected different type of exception", InvalidModelException.NO_OWNER, e.getType());
			
		}
		
	}
	
	@Test
	public void testListRelationshipOneToManyNoBackRef() {
		
		// arrange 
		
		ProjectDefinition projectDef = new ProjectDefinition("ContactManger", "");
		
		ListDefinition addresses = new ListDefinition("addresses", "Address");
		addresses.setRelationshipType(RelationshipType.oneToMany);
		
		ClassDefinition address = new ClassDefinition(projectDef, "Address", "", "");
		
		ClassDefinition contact = new ClassDefinition(projectDef, "Contact", "", "");
		contact.getListDefinitions().add(addresses);
		
 		projectDef.getClassDefinitions().add(address);
 		projectDef.getClassDefinitions().add(contact);
 		
		// act & assert 
		
		/*
		 * A contact has a list of addresses, so the address class should
		 * have a reference to the contact.
		 */
		
		try {
			
			classVal.validateRelationships(projectDef, contact);
			Assert.fail("Expected exception!");
			
		} catch (InvalidModelException e) {
			
			Assert.assertEquals("Expected different type of exception", InvalidModelException.NO_BACK_REF, e.getType());
			
		}
		
	}
	
	@Test
	public void testListRelationshipManyToManyTwoOwners() {
		
		// arrange 
		
		ProjectDefinition projectDef = new ProjectDefinition("School", "");
		
		ListDefinition stundents = new ListDefinition("students", "Student");
		stundents.setRelationshipType(RelationshipType.manyToMany);
		stundents.setOwner(true);
		
		ListDefinition courses = new ListDefinition("courses", "Course");
		courses.setRelationshipType(RelationshipType.manyToMany);
		courses.setOwner(true);
		
		ClassDefinition course = new ClassDefinition(projectDef, "Course", "", "");
		course.getListDefinitions().add(stundents);
		
		ClassDefinition student = new ClassDefinition(projectDef, "Student", "", "");
 		student.getListDefinitions().add(courses);
		
 		projectDef.getClassDefinitions().add(student);
 		projectDef.getClassDefinitions().add(course);
 		
		// act & assert 
		
		/*
		 * This is a many to many relationship between course and student, 
		 * and both sides declare to be the owner, so we expect the validation to fail.
		 */
		
		try {
			
			// we could also pass in course
			classVal.validateRelationships(projectDef, student);
			Assert.fail("Expected exception!");
			
		} catch (InvalidModelException e) {
			
			Assert.assertEquals("Expected different type of exception", InvalidModelException.TWO_OWNERS, e.getType());
			
		}
		
	}
	
}
