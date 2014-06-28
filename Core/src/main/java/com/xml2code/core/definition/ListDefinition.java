package com.xml2code.core.definition;

import com.xml2code.core.types.RelationshipType;

/**
 * A list definition defines a list of other data entities in a class definition.
 * 
 * @author dasnervtdoch
 */
public class ListDefinition implements IMemberDefinition {

	/**
	 * The name of this list.
	 */
	private String listName;
	
	/**
	 * The name of the type of items in the list.
	 */
	private String listItemType;
	
	/**
	 * Whether or not the relationship to the owned entities is unidirectional. 
	 * If true this mean that the owned entities have no reference to their owning entity. 
	 * If false the owned entities have a reference to the owning entity.
	 */
	private boolean unidirectional;
	
	/**
	 * If this reference is part of a bidirectional relationships this flag
	 * indicates if this the owning class in the relationship.
	 * For example a course has many students and a student takes many courses. 
	 * Every bidirectional relationship requires an owner, 
	 * otherwise the model is considered invalid. 
	 */
	private boolean owner;
	
	/**
	 * The relationship of this list to the owned entity.
	 */
	private RelationshipType relationshipType;
	
	/**
	 * Constructor
	 * 
	 * @param listName
	 * @param listItemType
	 */
	public ListDefinition(String listName, String listItemType) {
		
		this.listName = listName;
		this.listItemType = listItemType;
		this.unidirectional = false;
		this.owner = false;
		this.relationshipType = RelationshipType.oneToMany;
		
	}

	/**
	 * @return the listName
	 */
	public String getName() {
		return listName;
	}

	/**
	 * @param listName the listName to set
	 */
	public void setListName(String listName) {
		this.listName = listName;
	}

	/**
	 * @return the listItemType
	 */
	public String getType() {
		return listItemType;
	}

	/**
	 * @param listItemType the listItemType to set
	 */
	public void setListItemType(String listItemType) {
		this.listItemType = listItemType;
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
