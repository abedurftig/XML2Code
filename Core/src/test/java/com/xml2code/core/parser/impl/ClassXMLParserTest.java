package com.xml2code.core.parser.impl;

import java.io.File;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Test;

import util.TestResourceUtil;

import com.xml2code.core.definition.ProjectDefinition;
import com.xml2code.core.exception.XMLParseException;
import com.xml2code.core.parser.IClassXMLParser;

public class ClassXMLParserTest {

	private IClassXMLParser classXmlParser = new ClassXMLParser();
	
	@Test
	public void testClassModelIntegrityWrongNameFails() throws URISyntaxException {
		
		// arrange
		
		ProjectDefinition projectDef = new ProjectDefinition("Test-2", "");
		
		File classOneXmlFile = new File(TestResourceUtil.getResourceURI("/project-definitions/project-one/classes/MyClassWrongName.xml"));

		// act & assert
		
		try {
			
			projectDef.getClassDefinitions().add(classXmlParser.parseClassXML(projectDef, classOneXmlFile));
			Assert.fail("Expected an exception!");
			
		} catch (XMLParseException e) {
			
			//e.printStackTrace();
			
		}	
		
	}
	
}
