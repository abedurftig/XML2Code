package com.xml2code.core.definition;

import com.xml2code.core.types.RelationshipType;

/**
 * A reference definition defines a reference to another data entity
 * in a class definition.
 * 
 * @author dasnervtdoch
 */
public class ReferenceDefinition implements IMemberDefinition {

	/**
	 * The name of this reference.
	 */
	private String referenceName;
	
	/**
	 * The name of the type of data entity being referenced.
	 */
	private String referenceType;
	
	/**
	 * Whether or not the reference is required.
	 */
	private boolean required;
	
	/**
	 * Whether or not the relationship to the owned entity is unidirectional. 
	 * If true this mean that the owned entity have no reference to their owning entity. 
	 * If false the owned entity has a reference to the owning entity.
	 */
	private boolean unidirectional;
	
	/**
	 * If this reference is part of a bidirectional relationships this flag
	 * indicates if this the owning class is the owner in the relationship.
	 * For example a contact has (owns) an address, but the address has
	 * a reference to the owning contact. Every bidirectional relationship
	 * requires an owner, otherwise the model is considered invalid. 
	 */
	private boolean owner;
	
	/**
	 * Whether or not the owning entity has a one to many relationships
	 * to this reference type, ie. holds a list.
	 */
	private RelationshipType relationshipType;
	
	/**
	 * Constructor
	 * 
	 * @param referenceName the name of the reference
	 * @param referenceType the type of the reference
	 * @param required whether or not the reference is required
	 */
	public ReferenceDefinition(String referenceName, String referenceType, boolean required) {
		
		this.referenceName = referenceName;
		this.referenceType = referenceType;
		this.required = required;
		this.unidirectional = true;
		this.relationshipType = RelationshipType.oneToOne;
		this.owner = false;
		
	}

	/**
	 * @return the referenceName
	 */
	public String getName() {
		return referenceName;
	}

	/**
	 * @return the referenceType
	 */
	public String getType() {
		return referenceType;
	}
	
	/**
	 * @return true if the field is required; false otherwise
	 */
	public boolean isRequired() {
		return required;
	}
	
	/**
	 * @return the unidirectional
	 */
	public boolean isUnidirectional() {
		return unidirectional;
	}

	/**
	 * @param unidirectional the unidirectional to set
	 */
	public void setUnidirectional(boolean unidirectional) {
		this.unidirectional = unidirectional;
	}

	/**
	 * @return the owner
	 */
	public boolean isOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(boolean owner) {
		this.owner = owner;
	}

	/**
	 * @return the relationshipType
	 */
	public RelationshipType getRelationshipType() {
		return relationshipType;
	}

	/**
	 * @param relationshipType the relationshipType to set
	 */
	public void setRelationshipType(RelationshipType relationshipType) {
		this.relationshipType = relationshipType;
	}
	
}
