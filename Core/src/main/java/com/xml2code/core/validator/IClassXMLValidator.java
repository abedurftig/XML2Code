package com.xml2code.core.validator;

import com.xml2code.core.exception.XMLValidationException;

import java.io.File;

public interface IClassXMLValidator {

	public void validateClassXML(File classXml) throws XMLValidationException;

}
