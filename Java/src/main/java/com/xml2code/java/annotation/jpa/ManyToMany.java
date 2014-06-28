package com.xml2code.java.annotation.jpa;

import com.xml2code.core.definition.ClassDefinition;
import com.xml2code.core.definition.ListDefinition;
import com.xml2code.core.util.StringConstants;
import com.xml2code.core.util.StringUtil;
import com.xml2code.java.annotation.ListAnnotation;

public class ManyToMany extends JpaAnnotation implements ListAnnotation {

	private ListDefinition listDefinition;
	private ClassDefinition classDefinition;

	public ManyToMany(ListDefinition listDefinition, ClassDefinition classDefinition) {
		this.listDefinition = listDefinition;
		this.classDefinition = classDefinition;
	}
	
	@Override
	public String getCode() {
		
		if (this.listDefinition.isOwner()) {
			
		    return "@ManyToMany(" + StringConstants.NEW_LINE_INDENT + StringConstants.INDENT +
		    		"targetEntity=" + listDefinition.getType() + ".class," + StringConstants.NEW_LINE_INDENT + StringConstants.INDENT +
		    		"cascade={CascadeType.PERSIST, CascadeType.MERGE}" + StringConstants.NEW_LINE_INDENT +
		           ")" + StringConstants.NEW_LINE_INDENT +
		    	   "@JoinTable(" + StringConstants.NEW_LINE_INDENT + StringConstants.INDENT +
		           	"name=\"" + getJoinTableName() + "\", " + StringConstants.NEW_LINE_INDENT + StringConstants.INDENT + 
		            "joinColumns=@JoinColumn(name=\"" + 
		           	StringUtil.joinWithUnderscore(classDefinition.getClassName()) + "_ID\")," + StringConstants.NEW_LINE_INDENT + StringConstants.INDENT +
		            "inverseJoinColumns=@JoinColumn(name=\"" + 
		           	StringUtil.joinWithUnderscore(listDefinition.getType()) + "_ID\")" + StringConstants.NEW_LINE_INDENT +
		           ")";
			
			
		} else {
		
			return "@ManyToMany(" + StringConstants.NEW_LINE_INDENT + StringConstants.INDENT +
					"cascade = {CascadeType.PERSIST, CascadeType.MERGE}," + StringConstants.NEW_LINE_INDENT + StringConstants.INDENT + 
					"mappedBy = \"" + StringUtil.getPlural(this.classDefinition.getClassName()).toLowerCase() + "\"," + StringConstants.NEW_LINE_INDENT + StringConstants.INDENT + 
					"targetEntity = " + this.listDefinition.getType() + ".class" + StringConstants.NEW_LINE_INDENT +
           		   ")";
			
		}
		
	}

	public ListDefinition getListDefinition() {
		return this.listDefinition;
	}
	
	private String getJoinTableName() {
		
		String tableName = StringUtil.joinWithUnderscore(this.classDefinition.getClassName()) + "_" + StringUtil.getPlural(this.listDefinition.getType());
		return tableName.toUpperCase();
		
	}

}
