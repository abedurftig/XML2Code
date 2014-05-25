package com.xml2code.core.parser.impl;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.xml2code.core.definition.InstructionsDef;
import com.xml2code.core.definition.ProjectDefinition;
import com.xml2code.core.exception.XMLParseException;
import com.xml2code.core.parser.IProjectXMLParser;
import com.xml2code.core.types.DatabaseType;
import com.xml2code.core.util.LoggerUtil;

public class ProjectXMLParser implements IProjectXMLParser {

	private static final String XML_PROJECT = "Project";
	private static final String XML_PROJECT_NAME = "projectName";
	private static final String XML_PROJECT_TARGET_DIR = "targetDir";
	private static final String XML_PROJECT_DATABASE = "database";
	
	private static final String XML_INSTRUCTIONS = "Instructions";
	private static final String XML_INSTRUCTIONS_JAVA = "generateJava";
	private static final String XML_INSTRUCTIONS_ORM = "generateRelationalMapping";
	private static final String XML_INSTRUCTIONS_API = "generateApi";
	private static final String XML_INSTRUCTIONS_VALIDATION = "generateValidation";
	private static final String XML_INSTRUCTIONS_WEB = "generateWeb";
	
	private static Logger LOGGER = LoggerUtil.getApplicationLogger(); 
	
	public ProjectDefinition parseProjectXML(File projectXml) throws XMLParseException {

		ProjectDefinition projectDef = null;
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		
		try {
			
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(projectXml);
			doc.getDocumentElement().normalize();
			
			Element projectNode = (Element) doc.getElementsByTagName(XML_PROJECT).item(0);
			Element instructionsNode = (Element) projectNode.getElementsByTagName(XML_INSTRUCTIONS).item(0);
			
			InstructionsDef instructions = new InstructionsDef();
			instructions.setGenerateJava(getBoolean(instructionsNode, XML_INSTRUCTIONS_JAVA));
			instructions.setGenerateRelationalMapping(getBoolean(instructionsNode, XML_INSTRUCTIONS_ORM));
			instructions.setGenerateApi(getBoolean(instructionsNode, XML_INSTRUCTIONS_API));
			instructions.setGenerateValidation(getBoolean(instructionsNode, XML_INSTRUCTIONS_VALIDATION));
			instructions.setGenerateWeb(getBoolean(instructionsNode, XML_INSTRUCTIONS_WEB));
			
			String projectName = projectNode.getAttribute(XML_PROJECT_NAME);
			String targetDir = projectNode.getAttribute(XML_PROJECT_TARGET_DIR);
			DatabaseType dbType = DatabaseType.valueOf(projectNode.getAttribute(XML_PROJECT_DATABASE));
			
			projectDef = new ProjectDefinition(projectName, targetDir);
			projectDef.setInstructions(instructions);
			projectDef.setDatabaseType(dbType);
			
		} catch (ParserConfigurationException e) {
			
			LOGGER.error(e.getMessage());
			throw new XMLParseException(e.getMessage());
			
		} catch (SAXException e) {
			
			LOGGER.error(e.getMessage());
			throw new XMLParseException(e.getMessage());
			
		} catch (IOException e) {
			
			LOGGER.error(e.getMessage());
			throw new XMLParseException(e.getMessage());
			
		}
		
		
		return projectDef;

	}
	
	private static boolean getBoolean(Element element, String key) {
		
		return Boolean.parseBoolean(element.getAttribute(key));
		
	}

}
