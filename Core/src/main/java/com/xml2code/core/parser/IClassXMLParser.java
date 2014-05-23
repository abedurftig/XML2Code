package com.xml2code.core.parser;

import com.xml2code.core.definition.ClassDef;
import com.xml2code.core.definition.ProjectDef;
import com.xml2code.core.exception.XMLParseException;

import java.io.File;

public interface IClassXMLParser {

	public ClassDef parseClassXML(ProjectDef projectDef, File classXml) throws XMLParseException;

}
