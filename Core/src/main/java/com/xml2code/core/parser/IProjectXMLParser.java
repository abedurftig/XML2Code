package com.xml2code.core.parser;

import com.xml2code.core.definition.ProjectDef;
import com.xml2code.core.exception.XMLParseException;

import java.io.File;

public interface IProjectXMLParser {

	public ProjectDef parseProjectXML(File projectXml) throws XMLParseException;

}
