package com.xml2code.core.validator.impl;

import com.xml2code.core.exception.XMLValidationException;
import com.xml2code.core.util.LoggerUtil;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public abstract class XMLValidator {

	protected static Logger LOGGER = LoggerUtil.getValidatorLogger();

	public static final String PROJECT_VALIDATION_MODE = "project";
	public static final String CLASS_VALIDATION_MODE = "class";

	protected void validateXMLAgainstSchema(File xmlFile, InputStream isSchema, String mode) throws XMLValidationException {

		Source sourceFile = new StreamSource(xmlFile);
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

		try {

			Source schemaSource = new StreamSource(isSchema);
			Schema schema = schemaFactory.newSchema(schemaSource);
			Validator validator = schema.newValidator();
			validator.validate(sourceFile);
			LOGGER.info(sourceFile.getSystemId() + " is valid");

		}
		catch (SAXException e) {

			LOGGER.error("XMLValidator#validateXMLAgainstSchema - " + sourceFile.getSystemId() + " is NOT valid");
			LOGGER.error("XMLValidator#validateXMLAgainstSchema - Reason: " + e.getLocalizedMessage());
			throw new XMLValidationException(getErrorMessage(e, xmlFile, mode));

		}
		catch (IOException e) {

			LOGGER.error("XMLValidator#validateXMLAgainstSchema - Reason: " + e.getLocalizedMessage());
			throw new XMLValidationException(getErrorMessage(e, xmlFile, mode));

		}
	}

	protected String getErrorMessage(Exception e, File xmlFile, String mode) {

		if (mode.equals(PROJECT_VALIDATION_MODE)) {

			return "Project validation failed with reason: " + e.getLocalizedMessage() + " - [" + xmlFile.getName() + "]";

		}
		else if (mode.equals(CLASS_VALIDATION_MODE)) {

			return "Class validation failed with reason: " + e.getLocalizedMessage() + " - [" + xmlFile.getName() + "]";

		}
		else {

			return ("unknown validation mode");

		}

	}

}
