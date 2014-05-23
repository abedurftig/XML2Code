package com.xml2code.core.validator.impl;

import com.xml2code.core.exception.XMLValidationException;
import com.xml2code.core.util.ResourceUtil;
import com.xml2code.core.validator.IClassXMLValidator;

import java.io.File;
import java.io.InputStream;

public class ClassXMLValidator extends XMLValidator implements IClassXMLValidator {

	public void validateClassXML(File classXml) throws XMLValidationException {

		InputStream isSchema = ResourceUtil.getInputStream("/xml-schemas/class-definition.xsd");
		validateXMLAgainstSchema(classXml, isSchema, XMLValidator.CLASS_VALIDATION_MODE);

	}

}
