package com.xml2code.core.validator.impl;

import java.io.File;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import util.TestResourceUtil;

import com.xml2code.core.definition.ProjectDefinition;
import com.xml2code.core.exception.InvalidModelException;
import com.xml2code.core.exception.XMLParseException;
import com.xml2code.core.parser.IClassXMLParser;
import com.xml2code.core.parser.impl.ClassXMLParser;
import com.xml2code.core.validator.IProjectDefinitionValidator;

@RunWith(JUnit4.class)
public class ProjectDefinitionValidatorTest {

	private IClassXMLParser classXmlParser = new ClassXMLParser(); 
	private IProjectDefinitionValidator projectDefValidator = new ProjectDefinitionValidator();
	
	@Test
	public void testClassModelIntegrityFails() throws URISyntaxException {
		
		// arrange
		
		ProjectDefinition projectDef = new ProjectDefinition("Test-2", "");
		
		File classOneXmlFile = new File(TestResourceUtil.getResourceURI("/project-definitions/project-one/classes/MyClassOne.xml"));
		File classTwoXmlFile = new File(TestResourceUtil.getResourceURI("/project-definitions/project-one/classes/MyClassTwo.xml"));
		File classThreeXmlFile = new File(TestResourceUtil.getResourceURI("/project-definitions/project-one/classes/MyClassThree.xml"));

		// act & assert
		
		try {
			
			projectDef.getClassDefinitions().add(classXmlParser.parseClassXML(projectDef, classOneXmlFile));
			projectDef.getClassDefinitions().add(classXmlParser.parseClassXML(projectDef, classTwoXmlFile));
			projectDef.getClassDefinitions().add(classXmlParser.parseClassXML(projectDef, classThreeXmlFile));
			
			projectDefValidator.validateClassModelIntegrity(projectDef);
			Assert.fail("Expected an exception!");
			
		} catch (InvalidModelException e) {
			
			//e.printStackTrace();
			
		} catch (XMLParseException e) {
			
			//e.printStackTrace();
			
		}	
		
	}
	
}
