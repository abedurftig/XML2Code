package com.xml2code.core.parser.impl;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.xml2code.core.definition.ClassDefinition;
import com.xml2code.core.definition.FieldDefinition;
import com.xml2code.core.definition.ListDefinition;
import com.xml2code.core.definition.ProjectDefinition;
import com.xml2code.core.definition.ReferenceDefinition;
import com.xml2code.core.exception.XMLParseException;
import com.xml2code.core.parser.IClassXMLParser;
import com.xml2code.core.types.FieldType;
import com.xml2code.core.util.LoggerUtil;

public class ClassXMLParser implements IClassXMLParser {

	private static final String XML_CLASS = "Class";
	private static final String XML_CLASS_NAME = "className";
	private static final String XML_CLASS_DESC = "description";
	private static final String XML_CLASS_SUPER = "superClass";
	private static final String XML_CLASS_DISPLAY = "displayName";
	private static final String XML_CLASS_EXPORT = "exportable";

	private static final String XML_FIELD = "Field";
	private static final String XML_FIELD_NAME = "fieldName";
	private static final String XML_FIELD_TYPE = "fieldType";
	
	private static final String XML_REFERENCE = "Reference";
	private static final String XML_REFERENCE_NAME = "referenceName";
	private static final String XML_REFERENCE_TYPE = "referenceType";
	private static final String XML_REFERENCE_OWNER = "owner";
	
	private static final String XML_LIST = "List";
	private static final String XML_LIST_NAME = "listName";
	private static final String XML_LIST_ITEM_TYPE = "itemType";
	
	private static final String XML_REQUIRED = "required";
	
	private static Logger LOGGER = LoggerUtil.getApplicationLogger(); 
	
	public ClassDefinition parseClassXML(ProjectDefinition projectDef, File classXml) throws XMLParseException {

		ClassDefinition classDef = null;
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		
		try {
			
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(classXml);
			doc.getDocumentElement().normalize();
			
			Element classNode = (Element) doc.getElementsByTagName(XML_CLASS).item(0);
			
			String className = classNode.getAttribute(XML_CLASS_NAME);
			String classDescription = classNode.getAttribute(XML_CLASS_DESC);
			String superClassName = classNode.getAttribute(XML_CLASS_SUPER);
			String displayName = classNode.getAttribute(XML_CLASS_DISPLAY);
			boolean exportable = Boolean.parseBoolean(classNode.getAttribute(XML_CLASS_EXPORT));
			
			classDef = new ClassDefinition(projectDef, className, superClassName, classDescription, exportable);
			classDef.setDisplayName(displayName);
			
			String fileName = classXml.getName();
			if (!(classDef.getClassName() + ".xml").equalsIgnoreCase(fileName)) {
				throw new XMLParseException("The name of the file '"+ fileName +"' does not match the class name defined in the XML file.");
			}
			
			parseFieldDefinitions(classNode, classDef);
			parseReferenceDefinitions(classNode, classDef);
			parseListDefinitions(classNode, classDef);
			
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
		
		return classDef;		
		
	}
	
	private void parseFieldDefinitions(Element classNode, ClassDefinition classDef) {
		
		NodeList fieldDefinitionNodes = classNode.getElementsByTagName(XML_FIELD);
		
		for (int temp = 0; temp < fieldDefinitionNodes.getLength(); temp++) {
		
			Node nNode = fieldDefinitionNodes.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	 
				Element fieldDefElement = (Element) nNode;
				
				String fieldName = fieldDefElement.getAttribute(XML_FIELD_NAME);
				FieldType fieldType = FieldType.valueOf(fieldDefElement.getAttribute(XML_FIELD_TYPE));
				boolean required = Boolean.parseBoolean(fieldDefElement.getAttribute(XML_REQUIRED));
				
				FieldDefinition fieldDef = new FieldDefinition(fieldName, fieldType, required);
				classDef.getFieldDefinitions().add(fieldDef);
	 
			}	
			
		}
		
	}
	
	private void parseReferenceDefinitions(Element classNode, ClassDefinition classDef) {
		
		NodeList referenceDefinitionNodes = classNode.getElementsByTagName(XML_REFERENCE);
		
		for (int temp = 0; temp < referenceDefinitionNodes.getLength(); temp++) {
		
			Node nNode = referenceDefinitionNodes.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	 
				Element referenceDefElement = (Element) nNode;
				
				String referenceName = referenceDefElement.getAttribute(XML_REFERENCE_NAME);
				String referenceType = referenceDefElement.getAttribute(XML_REFERENCE_TYPE);
				boolean owner = Boolean.parseBoolean(referenceDefElement.getAttribute(XML_REFERENCE_OWNER));
				boolean required = Boolean.parseBoolean(referenceDefElement.getAttribute(XML_REQUIRED));
				
				ReferenceDefinition referenceDef = new ReferenceDefinition(referenceName, referenceType, required);
				referenceDef.setOwner(owner);
				
				classDef.getReferenceDefinitions().add(referenceDef);
	 
			}	
			
		}	
		
	}
	
	private void parseListDefinitions(Element classNode, ClassDefinition classDef) {
		
		NodeList listDefinitionNodes = classNode.getElementsByTagName(XML_LIST);
		
		for (int temp = 0; temp < listDefinitionNodes.getLength(); temp++) {
		
			Node nNode = listDefinitionNodes.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	 
				Element listDefElement = (Element) nNode;
				
				String listName = listDefElement.getAttribute(XML_LIST_NAME);
				String listItemType = listDefElement.getAttribute(XML_LIST_ITEM_TYPE);
				
				ListDefinition listDef = new ListDefinition(listName, listItemType);
				classDef.getListDefinitions().add(listDef);
	 
			}	
			
		}	
		
	}
	
}
