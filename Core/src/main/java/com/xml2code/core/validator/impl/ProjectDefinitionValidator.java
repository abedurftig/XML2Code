package com.xml2code.core.validator.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.xml2code.core.definition.ClassDefinition;
import com.xml2code.core.definition.ListDefinition;
import com.xml2code.core.definition.ProjectDefinition;
import com.xml2code.core.definition.ReferenceDefinition;
import com.xml2code.core.exception.InvalidModelException;
import com.xml2code.core.factory.ValidatorFactory;
import com.xml2code.core.util.LoggerUtil;
import com.xml2code.core.validator.IClassDefinitionValidator;
import com.xml2code.core.validator.IProjectDefinitionValidator;

public class ProjectDefinitionValidator implements IProjectDefinitionValidator {

	protected static Logger LOGGER = LoggerUtil.getValidatorLogger(); 
	
	/**
	 * Validates the XML file containing the project definition
	 * @param projectXML
	 * 
	 * @throws InvalidModelException 
	 */
	public void validateClassModelIntegrity(ProjectDefinition projectDef) throws InvalidModelException {
		
		Map<String, ClassDefinition> classDefMap = new HashMap<String, ClassDefinition>();
		
		// add all class definitions to the map and add 
		// all required types from reference- and list definitions
		Map<String, Set<String>> requiredTypes = new HashMap<String, Set<String>>();
		for (ClassDefinition classDef : projectDef.getClassDefinitions()) {
			
			classDefMap.put(classDef.getClassName(), classDef);
			addRequiredTypes(classDef, requiredTypes);
			
		}
		
		// check that all required types exist
		for (String requiredType : requiredTypes.keySet()) {
			
			if (classDefMap.get(requiredType) == null) {
				
				LOGGER.debug("Found missing dependency");
				String message = "The class '" + requiredType + 
							 	 "' does not exists but is required by the following classes " + 
							 	 requiredTypes.get(requiredType).toString();
				
				throw new InvalidModelException(InvalidModelException.MISSING_DEPENDENCY, message);
				
			}
			
		}
		
		validateRelationships(projectDef);
		
	}

	private void validateRelationships(ProjectDefinition projectDef) throws InvalidModelException {
		
		IClassDefinitionValidator classValidator = ValidatorFactory.getClassDefValidator(); 
		
		for (ClassDefinition classDef : projectDef.getClassDefinitions()) {
			
			classValidator.validateRelationships(projectDef, classDef);
			
		}		
		
	}
	
	/**
	 * Adds all required types from reference definitions and list definitions to the specified set. 
	 * Also adds the dependency to the super class if one was defined.
	 * 
	 * @param classDef the class definition which is currently evaluated
	 * @param requiredTypes the map contain a set of required types per class definition
	 */
	private void addRequiredTypes(ClassDefinition classDef, Map<String, Set<String>> requiredTypes) {
		
		Set<String> existingDependencies = null;
		
		String className = classDef.getClassName();
		String superClassName = classDef.getSuperClassName(); 
		
		// super class
		if (classDef.hasSuperClass()) {
			
			existingDependencies = requiredTypes.get(superClassName);
			
			if (existingDependencies == null) {
				existingDependencies = new HashSet<String>();
				requiredTypes.put(superClassName, existingDependencies);
			}
			
			existingDependencies.add(className);
			
		}
		
		// reference definitions
		for (ReferenceDefinition referenceDef : classDef.getReferenceDefinitions()) {
			
			existingDependencies = requiredTypes.get(referenceDef.getReferenceType());
			
			if (existingDependencies == null) {
				
				existingDependencies = new HashSet<String>();
				requiredTypes.put(referenceDef.getReferenceType(), existingDependencies);
				
			}
			
			existingDependencies.add(className);
			
		}
		
		// list definitions
		for (ListDefinition listDef : classDef.getListDefinitions()) {
			
			existingDependencies = requiredTypes.get(listDef.getListItemType());
			
			if (existingDependencies == null) {
				
				existingDependencies = new HashSet<String>();
				requiredTypes.put(listDef.getListItemType(), existingDependencies);
				
			}
			
			existingDependencies.add(className);
			
		}	
		
	}

}
