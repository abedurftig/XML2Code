package com.xml2code.java.generator.impl;

import com.xml2code.core.definition.ClassDefinition;
import com.xml2code.core.definition.FieldDefinition;
import com.xml2code.core.definition.IMemberDefinition;
import com.xml2code.core.definition.ListDefinition;
import com.xml2code.core.definition.ReferenceDefinition;
import com.xml2code.java.generator.IAnnotationGenerator;

public class AnnotationGenerator implements IAnnotationGenerator {

	public String getClassAnnotations(ClassDefinition classDefinition) {

		// TODO finish implementation
		return "";

	}

	public String getMemberAnnotations(IMemberDefinition memberDefinition) {
		
		if (memberDefinition instanceof FieldDefinition) {
			
			return getFieldAnnotations((FieldDefinition) memberDefinition);
			
		} else if (memberDefinition instanceof ReferenceDefinition) {
			
			return getReferenceAnnotations((ReferenceDefinition) memberDefinition);
			
		} else if (memberDefinition instanceof ListDefinition) {
			
			return getListAnnotations((ListDefinition) memberDefinition);
			
		}
		
		return "";
		
	}
	
	private String getFieldAnnotations(FieldDefinition fieldDefinition) {

		// TODO finish implementation
		return "";

	}

	private String getReferenceAnnotations(ReferenceDefinition referenceDefinition) {

		// TODO finish implementation
		return "";

	}

	private String getListAnnotations(ListDefinition listDefinition) {

		// TODO finish implementation
		return "";

	}

}
