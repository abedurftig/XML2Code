package com.xml2code.core.validator.impl;

import com.xml2code.core.exception.XMLValidationException;
import com.xml2code.core.util.ResourceUtil;
import com.xml2code.core.validator.IProjectXMLValidator;

import java.io.File;
import java.io.InputStream;

public class ProjectXMLValidator extends XMLValidator implements IProjectXMLValidator {

	public void validateProjectXML(File projectXml) throws XMLValidationException {

		InputStream isSchema = ResourceUtil.getInputStream("/xml-schemas/project-definition.xsd");
		validateXMLAgainstSchema(projectXml, isSchema, XMLValidator.PROJECT_VALIDATION_MODE);

	}

}
