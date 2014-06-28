package com.xml2code.core.validator.impl;

import com.xml2code.core.definition.ClassDefinition;
import com.xml2code.core.definition.ListDefinition;
import com.xml2code.core.definition.ProjectDefinition;
import com.xml2code.core.definition.ReferenceDefinition;
import com.xml2code.core.exception.InvalidModelException;
import com.xml2code.core.types.RelationshipType;
import com.xml2code.core.validator.IClassDefinitionValidator;

public class ClassDefinitionValidator implements IClassDefinitionValidator {

	public void validateRelationships(ProjectDefinition projectDef, ClassDefinition classDef) 
			throws InvalidModelException {

		for (ReferenceDefinition refDef : classDef.getReferenceDefinitions()) {

			if (refDef.getRelationshipType() == RelationshipType.oneToOne) { 

				ClassDefinition referencedClass = projectDef.getClassDefinitionByName(refDef.getType());
				ReferenceDefinition backRef = referencedClass.getReferenceOfType(classDef.getClassName());

				// just double checking
				if (refDef.isUnidirectional() && backRef != null) {
					
					throw new InvalidModelException(InvalidModelException.INVALID_BACK_REF,
							"Unidirectional reference cannot have a back-reference [class: "
									+ classDef.getClassName()
									+ ", reference to: "
									+ refDef.getType()
									+ "]");
					
				}

				// just double checking
				if (!refDef.isUnidirectional() && backRef == null) {
					
					throw new InvalidModelException(InvalidModelException.NO_BACK_REF,
							"Bidirectional reference has no birectional back-reference [class: "
									+ classDef.getClassName()
									+ ", reference to: "
									+ refDef.getType()
									+ "]");
					
				}

				if (!refDef.isUnidirectional() && refDef.isOwner() && backRef.isOwner()) {
					
					throw new InvalidModelException(InvalidModelException.TWO_OWNERS,
							"Not both sides of a relationship can be the owner [class: "
									+ classDef.getClassName()
									+ ", reference to: "
									+ refDef.getType()
									+ "]");				
					
				}

				if (!refDef.isUnidirectional() && !refDef.isOwner() && !backRef.isOwner()) {
					
					throw new InvalidModelException(InvalidModelException.NO_OWNER,
							"One side of a relationship must be the owner [class: "
									+ classDef.getClassName()
									+ ", reference to: "
									+ refDef.getType()
									+ "]");		
					
				}

			} else if (refDef.getRelationshipType() == RelationshipType.manyToOne) {

				ClassDefinition referencedClass = projectDef.getClassDefinitionByName(refDef.getType());
				ListDefinition listRef = referencedClass.getListOfType(classDef.getClassName());

				// just double checking
				if (listRef == null) {
					
					throw new InvalidModelException(InvalidModelException.NO_LIST,
							"Reference has 'manyToOne' relationship to a class "
									+ "which does not define a list of this type [class: "
									+ classDef.getClassName()
									+ ", reference to: "
									+ refDef.getType()
									+ "]");
					
				}

			} else {
				
				throw new InvalidModelException(InvalidModelException.INVALID_TYPE,
						"Reference has the wrong relationship type [type: "
								+ refDef.getRelationshipType()
								+ "], references can only be 'oneToOne' or 'manyToOne'");
				
			}
			
		}
		
		for (ListDefinition listDefinition : classDef.getListDefinitions()) {
			
			if (listDefinition.getRelationshipType() == RelationshipType.oneToMany) {
			
				String typeName = listDefinition.getType();
				ClassDefinition listItem = projectDef.getClassDefinitionByName(typeName);
				if (!listItem.hasReferenceToType(classDef.getClassName())) {
					
					throw new InvalidModelException(InvalidModelException.NO_BACK_REF,
							classDef.getClassName() + " has a list of type " + typeName
							+ ", but " + typeName + " does not define a reference to " 
							+ classDef.getClassName());
					
				}
				
			}
			
			if (listDefinition.getRelationshipType() == RelationshipType.manyToMany) {
				
				String typeName = listDefinition.getType();
				ClassDefinition listItem = projectDef.getClassDefinitionByName(typeName);
				ListDefinition itemDefintion = listItem.getListOfType(classDef.getClassName());
				
				boolean currentIsOwner = listDefinition.isOwner();
				boolean itemIsOwner = itemDefintion.isOwner();
				
				if (!(currentIsOwner || itemIsOwner)) {
					
					throw new InvalidModelException(InvalidModelException.NO_OWNER,
							classDef.getClassName() + " and " + listItem.getClassName()
								+ " have a many to many relationship. But no side is declared the owner of the relationship.");
					
				}
				
				if (currentIsOwner && itemIsOwner) {
					
					throw new InvalidModelException(InvalidModelException.TWO_OWNERS,
							classDef.getClassName() + " and " + listItem.getClassName()
								+ " have a many to many relationship. And both sides declare to be the owner of the relationship.");
					
				}
				
			}
			
		}

	}

}
