package com.xml2code.java.generator;

import com.xml2code.core.definition.ClassDefinition;
import com.xml2code.core.definition.FieldDefinition;
import com.xml2code.core.definition.ListDefinition;
import com.xml2code.core.definition.ReferenceDefinition;

public interface IAnnotationGenerator {

	public String getFieldAnnotations(FieldDefinition fieldDefinition);

	public String getReferenceAnnotations(ReferenceDefinition referenceDefinition);

	public String getListAnnotations(ListDefinition listDefinition);

	public String getClassAnnotations(ClassDefinition classDefinition);

}
