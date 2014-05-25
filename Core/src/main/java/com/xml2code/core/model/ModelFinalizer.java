package com.xml2code.core.model;

import com.xml2code.core.definition.ClassDefinition;
import com.xml2code.core.definition.ListDefinition;
import com.xml2code.core.definition.ProjectDefinition;
import com.xml2code.core.definition.ReferenceDefinition;
import com.xml2code.core.types.RelationshipType;

public class ModelFinalizer {

	public static void finalize(ProjectDefinition projectDef) {}

	public static void determineRelationships(ProjectDefinition projectDef) {
		
		for (ClassDefinition classDef : projectDef.getClassDefinitions()) {
			
			determineReferenceRelationships(classDef);
			determineListRelationships(classDef);
			
		}
		
	}
	
	/**
	 * Determines the relationship of every reference definition contained in the class definition.
	 */
	private static void determineReferenceRelationships(ClassDefinition classDef) {
		
		ProjectDefinition projectDef = classDef.getProjectDefinition();
		String className = classDef.getClassName();
		
		for (ReferenceDefinition refDef : classDef.getReferenceDefinitions()) {
			
			ClassDefinition ownedClass = projectDef.getClassDefinitionByName(refDef.getReferenceType());
			
			boolean hasReference = ownedClass.hasReferenceToType(className);
			refDef.setUnidirectional(!hasReference);
			
			boolean manyToOne = ownedClass.hasListOfType(className);
			if (manyToOne) {
				
				refDef.setRelationshipType(RelationshipType.manyToOne);
				
			} else {
				
				refDef.setRelationshipType(RelationshipType.oneToOne);
				
			}
			
		}
		
	}
	
	/**
	 * Determines the relationship of every list definition contained in the class definition.
	 */
	private static void determineListRelationships(ClassDefinition classDef) {
		
		ProjectDefinition projectDef = classDef.getProjectDefinition();
		String className = classDef.getClassName();
		
		for (ListDefinition listDef : classDef.getListDefinitions()) {
			
			ClassDefinition ownedClass = projectDef.getClassDefinitionByName(listDef.getListItemType());
			
			boolean hasReference = ownedClass.hasReferenceToType(className);
			listDef.setUnidirectional(!hasReference);
			
			boolean manyToMany = ownedClass.hasListOfType(className);
			if (manyToMany) {
				
				listDef.setRelationshipType(RelationshipType.manyToMany);
				
			} else {
				
				listDef.setRelationshipType(RelationshipType.oneToMany);
				
			}	
			
		}
		
	}
	
}
