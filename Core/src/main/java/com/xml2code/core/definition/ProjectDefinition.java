package com.xml2code.core.definition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.xml2code.core.types.DatabaseType;

/**
 * This class represents a XML2Java project.
 * 
 * @author dasnervtdoch
 */
public class ProjectDefinition {

	/**
	 * Used to cache class definition objects for faster retrieval.
	 */
	private Map<String, ClassDefinition> classMap = new HashMap<String, ClassDefinition>();
	
	/**
	 * The parent folder of the generated Java project.
	 */
	private String targetDir;
	
	/**
	 * The name of the generated Java project.
	 */
	private String projectName; 

	/**
	 * The type of database which should be configured in the Hibernate configuration; default is H2.
	 */
	private DatabaseType databaseType = DatabaseType.h2;
	
	/**
	 * A list of class definition objects.
	 */
	private List<ClassDefinition> classDefinitions;

	/**
	 * The generation instructions for this project.
	 */
	private InstructionsDefinition instructions;
	
	/**
	 * Constructor
	 * 
	 * @param projectName the name of the project
	 * @param targetDir the path to target directory
	 */
	public ProjectDefinition(String projectName, String targetDir) {
		
		this.projectName = projectName;
		this.targetDir = targetDir;
		this.classDefinitions = new ArrayList<ClassDefinition>();
		this.instructions = new InstructionsDefinition();
	}

	/**
	 * @return the parent folder of the generated Java project
	 */
	public String getTargetDir() {
		return targetDir;
	}

	/**
	 * @param targetDir
	 */
	public void setTargetDir(String targetDir) {
		this.targetDir = targetDir;
	}
	
	/**
	 * @return the name of the project
	 */
	public String getProjectName() {
		return projectName;
	}
	
	/**
	 * @return the class definitions
	 */
	public List<ClassDefinition> getClassDefinitions() {
		return classDefinitions;
	}

	/**
	 * @return the databaseType
	 */
	public DatabaseType getDatabaseType() {
		return databaseType;
	}

	/**
	 * @return the instructions
	 */
	public InstructionsDefinition getInstructions() {
		return instructions;
	}

	/**
	 * @param instructions the instructions to set
	 */
	public void setInstructions(InstructionsDefinition instructions) {
		this.instructions = instructions;
	}

	/**
	 * @param databaseType the database type to set
	 */
	public void setDatabaseType(DatabaseType databaseType) {
		this.databaseType = databaseType;
	}
	
	// ------------------------------------------
	// Public methods
	// ------------------------------------------
	
	/**
	 * Checks if the project definition contains a class definition
	 * with the specified name and returns it that is the case.
	 * This method returns <code>null</code> if no class definition
	 * with the specified name exists.
	 * 
	 * @param className the name of the class definition which should be returned
	 * @return the class definition with the specified name
	 */
	public ClassDefinition getClassDefinitionByName(String className) {
		
		if (classMap.containsKey(className)) {
			return classMap.get(className);
		} else {
			
			ClassDefinition classDef = null;
			Iterator<ClassDefinition> classDefIter = classDefinitions.iterator();
			
			while (classDef == null && classDefIter.hasNext()) {
				
				ClassDefinition next = classDefIter.next();
				if (next.getClassName().equals(className)) {
					classMap.put(className, next);
					classDef = next;
				}
			}
			
			return classDef;
		}
		
	}
	
}
