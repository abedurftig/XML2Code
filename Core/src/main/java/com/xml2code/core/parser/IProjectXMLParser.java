package com.xml2code.core.parser;

import com.xml2code.core.definition.ProjectDefinition;
import com.xml2code.core.exception.XMLParseException;

import java.io.File;

public interface IProjectXMLParser {

	public ProjectDefinition parseProjectXML(File projectXml) throws XMLParseException;

}
