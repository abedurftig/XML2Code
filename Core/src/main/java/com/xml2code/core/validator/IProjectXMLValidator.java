package com.xml2code.core.validator;

import com.xml2code.core.exception.XMLValidationException;

import java.io.File;

public interface IProjectXMLValidator {

	public void validateProjectXML(File projectXml) throws XMLValidationException;

}
