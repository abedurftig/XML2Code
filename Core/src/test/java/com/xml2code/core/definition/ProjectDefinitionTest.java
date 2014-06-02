package com.xml2code.core.definition;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import util.TestPropertiesUtil;

import com.xml2code.core.model.ModelFinalizer;
import com.xml2code.core.types.RelationshipType;

/**
 * Unit tests for class: {@link com.xml2code.core.definition.ProjectDefinition}
 */
@RunWith(JUnit4.class)
public class ProjectDefinitionTest {

	@Test
	public void testHasOneToOneBidirectional() {
		
		// arrange
		
		ProjectDefinition projectDef = new ProjectDefinition("Test-3", TestPropertiesUtil.getTestOutputDirectory());
		
		ClassDefinition classDefOne = new ClassDefinition(projectDef, "TestClass1", "", "This is test class 1");
		ReferenceDefinition refDefOne = new ReferenceDefinition("testClass2", "TestClass2", false);
		classDefOne.getReferenceDefinitions().add(refDefOne);
		
		ClassDefinition classDefTwo = new ClassDefinition(projectDef, "TestClass2", "", "This is test class 2");
		ReferenceDefinition refDefTwo = new ReferenceDefinition("testClass1", "TestClass1", false);
		classDefTwo.getReferenceDefinitions().add(refDefTwo);
		
		projectDef.getClassDefinitions().add(classDefOne);
		projectDef.getClassDefinitions().add(classDefTwo);
		
		// act
		
		ModelFinalizer.determineRelationships(projectDef);
		
		// assert 
		
		Assert.assertFalse("failure - TestClass1 should have bidirectional rel to TestClass2", refDefOne.isUnidirectional());
		Assert.assertFalse("failure - TestClass1 should not be oneToMany", refDefOne.getRelationshipType() == RelationshipType.oneToMany);

		Assert.assertFalse("failure - TestClass1 should have bidirectional rel to TestClass1", refDefTwo.isUnidirectional());
		Assert.assertFalse("failure - TestClass1 should not be oneToMany", refDefOne.getRelationshipType() == RelationshipType.oneToMany);
		
	}
	
	@Test
	public void testHasOneToOneUnidirectional() {
		
		// arrange
		
		ProjectDefinition projectDef = new ProjectDefinition("Test-3", TestPropertiesUtil.getTestOutputDirectory());
		
		ClassDefinition classDefOne = new ClassDefinition(projectDef, "TestClass1", "", "This is test class 1");
		ReferenceDefinition refDefOne = new ReferenceDefinition("testClass2", "TestClass2", false);
		classDefOne.getReferenceDefinitions().add(refDefOne);
		
		ClassDefinition classDefTwo = new ClassDefinition(projectDef, "TestClass2", "", "This is test class 2");
		
		projectDef.getClassDefinitions().add(classDefOne);
		projectDef.getClassDefinitions().add(classDefTwo);
		
		// act
		
		ModelFinalizer.determineRelationships(projectDef);
		
		// assert 
		
		Assert.assertTrue("failure - TestClass1 should have unidirection rel to TestClass2", refDefOne.isUnidirectional());
		Assert.assertFalse("failure - TestClass1 should not be oneToMany", refDefOne.getRelationshipType() == RelationshipType.oneToMany);
		
	}
	
}
