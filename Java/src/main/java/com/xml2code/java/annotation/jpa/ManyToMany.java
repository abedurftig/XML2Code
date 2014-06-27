package com.xml2code.java.annotation.jpa;

import com.xml2code.core.definition.ListDefinition;
import com.xml2code.java.annotation.ListAnnotation;

public class ManyToMany extends JpaAnnotation implements ListAnnotation {

	private ListDefinition listDefinition;

	public ManyToMany(ListDefinition listDefinition) {
		this.listDefinition = listDefinition;
	}
	
	@Override
	public String getCode() {
		return "@ManyToMany";
	}

	public ListDefinition getListDefinition() {
		return this.listDefinition;
	}

}
