package com.xml2code.core.parser.impl;

import java.io.File;
import java.net.URISyntaxException;

import com.xml2code.core.definition.InstructionsDefinition;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import util.TestResourceUtil;

import com.xml2code.core.definition.ProjectDefinition;
import com.xml2code.core.exception.XMLParseException;
import com.xml2code.core.parser.IProjectXMLParser;
import com.xml2code.core.types.DatabaseType;

@RunWith(JUnit4.class)
public class ProjectXMLParserTest {

	@Test
	public void parseProjectXMLSuccessfully() throws URISyntaxException, XMLParseException {
		
		File projectXmlFile = new File(TestResourceUtil.getResourceURI("/project-definitions/project-one/xml2code-project.xml"));
		IProjectXMLParser projectXmlParser = new ProjectXMLParser();
		
		ProjectDefinition projectDef = projectXmlParser.parseProjectXML(projectXmlFile);
		
		Assert.assertEquals("Expected different project name", "MyProject", projectDef.getProjectName());
		Assert.assertEquals("Expected different target dir", "/tmp", projectDef.getTargetDir());
		Assert.assertEquals("Expected different database", DatabaseType.h2, projectDef.getDatabaseType());
		
		InstructionsDefinition instructions = projectDef.getInstructions();
		
		Assert.assertNotNull("Failure - Generation instructions should not be null", instructions);
		
		Assert.assertEquals("Expected different ORM instruction", true, instructions.isGenerateRelationalMapping());
		Assert.assertEquals("Expected different Validation instruction", true, instructions.isGenerateValidation());
		Assert.assertEquals("Expected different API instruction", true, instructions.isGenerateApi());
		Assert.assertEquals("Expected different Web instruction", true, instructions.isGenerateWeb());
		Assert.assertEquals("Expected different Java instruction", true, instructions.isGenerateJava());
		
	}
	
}
