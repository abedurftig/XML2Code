package com.xml2code.java.generator;

import com.xml2code.core.definition.ClassDefinition;
import com.xml2code.core.definition.IMemberDefinition;

public interface IAnnotationGenerator {

	public String getClassAnnotations(ClassDefinition classDefinition);
	
	public String getMemberAnnotations(IMemberDefinition memberDefinition, ClassDefinition classDefinition);

}
