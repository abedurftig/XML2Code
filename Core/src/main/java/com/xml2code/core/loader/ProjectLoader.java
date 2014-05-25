package com.xml2code.core.loader;

import java.io.File;

import org.apache.log4j.Logger;

import com.xml2code.core.definition.ClassDefinition;
import com.xml2code.core.definition.ProjectDefinition;
import com.xml2code.core.exception.InvalidModelException;
import com.xml2code.core.exception.ProjectLoadFailedException;
import com.xml2code.core.exception.XMLParseException;
import com.xml2code.core.exception.XMLValidationException;
import com.xml2code.core.factory.ValidatorFactory;
import com.xml2code.core.model.ModelFinalizer;
import com.xml2code.core.parser.IClassXMLParser;
import com.xml2code.core.parser.IProjectXMLParser;
import com.xml2code.core.parser.impl.ClassXMLParser;
import com.xml2code.core.parser.impl.ProjectXMLParser;
import com.xml2code.core.util.LoggerUtil;
import com.xml2code.core.util.PropertiesUtil;
import com.xml2code.core.validator.IClassXMLValidator;
import com.xml2code.core.validator.IProjectDefinitionValidator;
import com.xml2code.core.validator.IProjectXMLValidator;
import com.xml2code.core.xml.XMLFileFilter;

/** 
 * This class represents the entry point to the core project.
 * The <code>ProjectLoader</code> provides an interface which let
 * the caller retrieve a <code>ProjectDef</code> which will be based
 * on the project definition files in the specified folder.
 * 
 * @author dasnervtdoch
 *
 */
public class ProjectLoader {
	
	protected static Logger LOGGER = LoggerUtil.getApplicationLogger();
	
	/**
	 * This method loads the project resources, validates, parses them and builds up 
	 * a <code>ProjectDef</code> instance which represents the specified project.
	 * 
	 * @param projectFolder the folder which contains the <code>xml2code-project.xml</code> file and the classes folder
	 * @return the parsed and build up project definition
	 * @throws ProjectLoadFailedException
	 */
	public static ProjectDefinition loadProject(String projectFolder) throws ProjectLoadFailedException {
		
		ProjectDefinition projectDef = null;
		
		File projectXmlFile = new File(projectFolder + "/" + PropertiesUtil.getProjectFileName());
		File[] classXmlFiles = null;
		
		// validate the project structure and XML files
		
		validateProjectDefinitionXML(projectXmlFile);
		classXmlFiles = validateClassDefinitionsXML(projectFolder);
		
		// parse the XML files
		
		projectDef = parseProjectDefinition(projectXmlFile, classXmlFiles);
		
		ModelFinalizer.determineRelationships(projectDef);
		
		// validate the project and class model integrity
		
		validateProjectModelIntegrity(projectDef);
		
		// finalize the model
		
		ModelFinalizer.finalize(projectDef);
		
		return projectDef;
		
	}
	
	private static void validateProjectDefinitionXML(File projectXmlFile) throws ProjectLoadFailedException {
		
		IProjectXMLValidator projectXMLValidator = ValidatorFactory.getProjectXMLValidator();
		
		if (projectXmlFile.exists() == false) {
			
			LOGGER.error(ProjectLoadFailedException.NO_PROJECT);
    		throw new ProjectLoadFailedException(ProjectLoadFailedException.NO_PROJECT);
			
		}
		
		try {
			
			LOGGER.info("Found project definition - " + projectXmlFile.getName());
			projectXMLValidator.validateProjectXML(projectXmlFile);
			
		} catch (XMLValidationException xve) {
			
			throw new ProjectLoadFailedException(xve);
			
		}
		
	}
	
	private static File[] validateClassDefinitionsXML(String projectRoot) throws ProjectLoadFailedException {
		
		IClassXMLValidator classXMLValidator = ValidatorFactory.getClassXMLValidator();
		
    	File classesXml = new File(projectRoot + "/classes"); 
    	
    	if (classesXml.exists() == false) {
    		
    		LOGGER.error(ProjectLoadFailedException.NO_CLASS_DEF_DIRECTORY);
    		throw new ProjectLoadFailedException(ProjectLoadFailedException.NO_CLASS_DEF_DIRECTORY);
    		
    	}
    	
    	File[] classXmlFiles = classesXml.listFiles(new XMLFileFilter());
    	for (File classXml : classXmlFiles) {
    		
    		try {
    		
    			LOGGER.info("Found class definition - " + classXml.getName());
    			classXMLValidator.validateClassXML(classXml);
    		
    		} catch (Exception e) {
    			
    			if (e instanceof ProjectLoadFailedException) {
    				
    				throw (ProjectLoadFailedException) e;
    				
    			} else {
    				
    				throw new ProjectLoadFailedException(e);
    				
    			}
    			
    		}
    		
    	}
    	
    	return classXmlFiles;
		
	}
	
    private static ProjectDefinition parseProjectDefinition(File projectXmlFile, File[]classXmlFiles) throws ProjectLoadFailedException {
    	
    	IProjectXMLParser projectXMLParser = new ProjectXMLParser();
    	IClassXMLParser classXMLParser = new ClassXMLParser();
    	
    	ProjectDefinition projectDef = null;
    	
    	try {
    		
    		projectDef = projectXMLParser.parseProjectXML(projectXmlFile);
        	if (projectDef.getTargetDir().isEmpty()) {
        		
        		projectDef.setTargetDir(projectXmlFile.getParent());
        		
        	}
        	
        	for (File classXml : classXmlFiles) {
        		
        		ClassDefinition classDef = classXMLParser.parseClassXML(projectDef, classXml);
        		projectDef.getClassDefinitions().add(classDef);
        		
        	}
        	
    	} catch (XMLParseException xpe) {
    		
    		throw new ProjectLoadFailedException(xpe);
    		
    	}
    	
    	return projectDef;
    	
    }
    
    private static void validateProjectModelIntegrity(ProjectDefinition projectDef) throws ProjectLoadFailedException {
    	
    	IProjectDefinitionValidator projectValidator = ValidatorFactory.getProjectDefValidator();
    	
    	try {
    		
			projectValidator.validateClassModelIntegrity(projectDef);
			
		} catch (InvalidModelException ime) {
			
			throw new ProjectLoadFailedException(ime);
			
		}
    	
    }
	
}
