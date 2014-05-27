package com.xml2code.core.definition;

import java.util.ArrayList;
import java.util.List;

import com.xml2code.core.types.FieldType;

/**
 * An object of this class represents a single class to be created.
 * 
 * @author dasnervtdoch
 */
public class ClassDefinition {

	/**
	 * The name of the default super class of every entity being generated.
	 */
	public static final String DEFAULT_SUPERCLASS = "DomainObject";
	
	/**
	 * The project definition the class represented by a class definition belongs to.
	 */
	private ProjectDefinition projectDefinition;
	
	/**
	 * The name of the class to be created.
	 */
	private String className;
	
	/**
	 * The super class of the class to be created. If not specified the new class 
	 * will extend the default entity class.
	 */
	private String superClassName = DEFAULT_SUPERCLASS;
	
	/**
	 * Description added as Javadoc style comment to the generated class.
	 */
	private String classDescription;
	
	/**
	 * Whether or not a REST API should be generated for this class.
	 */
	private boolean exportable = false;
	
	/**
	 * A string which will be used to generate the display name property for the generated class.
	 */
	private String displayName = "";
	
	/**
	 * A list of field definition objects.
	 */
	private List<FieldDefinition> fieldDefinitions;

	/**
	 * A list of reference definition objects.
	 */
	private List<ReferenceDefinition> referenceDefinitions;

	/**
	 * A list of list definition objects.
	 */
	private List<ListDefinition> listDefinitions;	
	
	/**
	 * Whether the generated class is the super class 
	 * of other domain classes. The generated class is abstract. 
	 */
	private boolean superClass = false;
	
	/**
	 * Constructor
	 * 
	 * @param projectDefinition a reference to the parent project definition
	 * @param className the name of the class to create
	 * @param superClassName the name of the super class of the class to create
	 * @param classDescription a description of the class will be added as a Javadoc style class comment
	 */
	public ClassDefinition(ProjectDefinition projectDefinition, String className, String superClassName, String classDescription) {
		
		this(projectDefinition, className, superClassName, classDescription, false);
		
	}

	/**
	 * Constructor
	 * 
	 * @param projectDefinition a reference to the parent project definition
	 * @param className the name of the class to create
	 * @param superClassName the name of the super class of the class to create
	 * @param classDescription a description of the class will be added as a Javadoc style class comment
	 */
	public ClassDefinition(ProjectDefinition projectDefinition, String className, String superClassName, String classDescription, boolean exportable) {
		
		this.projectDefinition = projectDefinition;
		this.className = className;
		this.superClassName = superClassName.isEmpty() ? DEFAULT_SUPERCLASS : superClassName;
		this.classDescription = classDescription;
		this.exportable = exportable;
		
		this.fieldDefinitions = new ArrayList<FieldDefinition>();
		this.referenceDefinitions = new ArrayList<ReferenceDefinition>();
		this.listDefinitions = new ArrayList<ListDefinition>();
		
	}
	
	// ------------------------------------------
	// Getters & Setters
	// ------------------------------------------
	
	/**
	 * @return the project definition this class definition is related to
	 */
	public ProjectDefinition getProjectDefinition() {
		return projectDefinition;
	}
	
	/**
	 * @return the name of the class to create
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @return the name of the super class of the class to create
	 */
	public String getSuperClassName() {
		return superClassName;
	}

	/**
	 * @return the Javadoc style class comment
	 */
	public String getClassDescription() {
		return classDescription;
	}
	
	/**
	 * @return whether or not a REST API should be generated
	 */
	public boolean isExportable() {
		return exportable;
	}
	
	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @return a list of field definition objects
	 */
	public List<FieldDefinition> getFieldDefinitions() {
		return fieldDefinitions;
	}

	/**
	 * @return a list of reference definition objects
	 */
	public List<ReferenceDefinition> getReferenceDefinitions() {
		return referenceDefinitions;
	}

	/**
	 * @return a list of list definition objects
	 */
	public List<ListDefinition> getListDefinitions() {
		return listDefinitions;
	}

	/**
	 * @return the superClass
	 */
	public boolean isSuperClass() {
		return superClass;
	}

	/**
	 * @param superClass the superClass to set
	 */
	public void setSuperClass(boolean superClass) {
		this.superClass = superClass;
	}
	
	// ------------------------------------------
	// Public methods
	// ------------------------------------------

	/**
	 * Gets the reference to the specified type.
	 * 
	 * @param typeName the name of the type in question
	 * @return the reference to the specified type if exists; returns <code>null</code> if not 
	 */
	public ReferenceDefinition getReferenceOfType(String typeName) {

		for (ReferenceDefinition refDef : referenceDefinitions) {
			if (refDef.getReferenceType().equals(typeName)) {
				return refDef;
			}
		}
		
		return null;
		
	}
	
	/**
	 * Gets a list definition of the specified type.
	 * 
	 * @param typeName typeName the name of the type in question
	 * @return a list definition of the specified type if exists; returns <code>null</code> if not
	 */
	public ListDefinition getListOfType(String typeName) {

		for (ListDefinition listDef : listDefinitions) {
			if (listDef.getListItemType().equals(typeName)) {
				return listDef;
			}
		}
		
		return null;
		
	}
	
	/**
	 * Returns the field definition with the specified name.
	 * 
	 * @return the field definition with the specified name; null if no field definition with that name exists
	 */
	public FieldDefinition getFieldDefinitionByName(String fieldName) {
		
		for (FieldDefinition fieldDef : fieldDefinitions) {
			if (fieldDef.getFieldName().equals(fieldName)) {
				return fieldDef;
			}
		}
		
		return null;	
		
	}
	
	public List<FieldDefinition> getAllEditableFields() {
		
		List<FieldDefinition> editableFields = new ArrayList<FieldDefinition>();
		
		editableFields.addAll(getInheritedFields());
		editableFields.addAll(getEditableFields());
		
		return editableFields;
		
	}
	
	/**
	 * Checks whether or not this class definition has a decimal field.
	 * 
	 * @return true if the class definition contains at least field of type decimal
	 */
	public boolean hasDecimal() {
		return hasFieldType(FieldType.decimal);
	}

	/**
	 * Checks whether or not this class definition has a data field.
	 * 
	 * @return true if the class definition contains at least field of type data
	 */
	public boolean hasDate() {
		return hasFieldType(FieldType.date);
	}

	/**
	 * Checks whether or not this class definition has a reference to the specified type.
	 * 
	 * @param typeName the name of type in question
	 * @return true if the class definition has a reference to the specified type
	 */
	public boolean hasReferenceToType(String typeName) {
		return getReferenceOfType(typeName) != null;	
	}
	
	/**
	 * Checks whether or not this class definition has a super class defined.
	 * 
	 * @return true if the class definition has a super class defined
	 */
	public boolean hasSuperClass() {
		return !superClassName.equals(DEFAULT_SUPERCLASS); 
	}

	/**
	 * Checks whether or not this class definition has a list of the specified type. 
	 * 
	 * @param typeName typeName the name of the type in question
	 * @return true if the class definition has a list of the specified type
	 */
	public boolean hasListOfType(String typeName) {
		
		for (ListDefinition listDef : listDefinitions) {
			if (listDef.getListItemType().equals(typeName)) {
				return true;
			}
		}
		
		return false;
		
	}
	
	// ------------------------------------------
	// Private methods
	// ------------------------------------------
	
	/**
	 * Checks whether or not this class definition has a field of the specified type.
	 * 
	 * @param type the field type in question
	 * @return true if the class definition contains at least field of the specified type
	 */
	private boolean hasFieldType(FieldType type) {
		
		for (FieldDefinition fieldDef : fieldDefinitions) {
			if (fieldDef.getFieldType() == type) {
				return true;
			}
		}
		
		return false;
		
	}
	
	private List<FieldDefinition> getInheritedFields() {
		
		List<FieldDefinition> inheritedFields = new ArrayList<FieldDefinition>();
		
		if (this.hasSuperClass()) {
			
			ClassDefinition superClass = projectDefinition.getClassDefinitionByName(superClassName);
			inheritedFields.addAll(superClass.getInheritedFields());
			inheritedFields.addAll(superClass.getEditableFields());
			
		}
		
		return inheritedFields;
		
	}
	
	private List<FieldDefinition> getEditableFields() {
		
		List<FieldDefinition> editableFields = new ArrayList<FieldDefinition>();
		
		for (FieldDefinition fieldDefinition : fieldDefinitions) {
			
			if (fieldDefinition.isEditable()) {
				
				editableFields.add(fieldDefinition);
				
			}
			
		}
		
		return editableFields;
		
	}
	
}
