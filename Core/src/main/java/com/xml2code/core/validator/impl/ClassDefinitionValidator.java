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

				ClassDefinition referencedClass = projectDef.getClassDefinitionByName(refDef.getReferenceType());
				ReferenceDefinition backRef = referencedClass.getReferenceOfType(classDef.getClassName());

				// just double checking
				if (refDef.isUnidirectional() && backRef != null) {
					
					throw new InvalidModelException(InvalidModelException.INVALID_BACK_REF,
							"Unidirectional reference cannot have a back-reference [class: "
									+ classDef.getClassName()
									+ ", reference to: "
									+ refDef.getReferenceType()
									+ "]");
					
				}

				// just double checking
				if (!refDef.isUnidirectional() && backRef == null) {
					
					throw new InvalidModelException(InvalidModelException.NO_BACK_REF,
							"Bidirectional reference has no birectional back-reference [class: "
									+ classDef.getClassName()
									+ ", reference to: "
									+ refDef.getReferenceType()
									+ "]");
					
				}

				if (!refDef.isUnidirectional() && refDef.isOwner() && backRef.isOwner()) {
					
					throw new InvalidModelException(InvalidModelException.TWO_OWNERS,
							"Not both sides of a relationship can be the owner [class: "
									+ classDef.getClassName()
									+ ", reference to: "
									+ refDef.getReferenceType()
									+ "]");				
					
				}

				if (!refDef.isUnidirectional() && !refDef.isOwner() && !backRef.isOwner()) {
					
					throw new InvalidModelException(InvalidModelException.NO_OWNER,
							"One side of a relationship must be the owner [class: "
									+ classDef.getClassName()
									+ ", reference to: "
									+ refDef.getReferenceType()
									+ "]");		
					
				}

			} else if (refDef.getRelationshipType() == RelationshipType.manyToOne) {

				ClassDefinition referencedClass = projectDef.getClassDefinitionByName(refDef.getReferenceType());
				ListDefinition listRef = referencedClass.getListOfType(classDef.getClassName());

				// just double checking
				if (listRef == null) {
					
					throw new InvalidModelException(InvalidModelException.NO_LIST,
							"Reference has 'manyToOne' relationship to a class "
									+ "which does not define a list of this type [class: "
									+ classDef.getClassName()
									+ ", reference to: "
									+ refDef.getReferenceType()
									+ "]");
					
				}

			} else {

				throw new InvalidModelException(InvalidModelException.INVALID_TYPE,
						"Reference has the wrong relationship type [type: "
								+ refDef.getRelationshipType()
								+ ", references can only be 'oneToOne' or 'manyToOne'");
				
			}
			
		}

	}

}
