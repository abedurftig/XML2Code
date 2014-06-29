package com.xml2code.java.generator;

import java.util.Set;

import com.xml2code.core.definition.ClassDefinition;
import com.xml2code.core.definition.IMemberDefinition;
import com.xml2code.java.annotation.Annotation;

public interface IAnnotationGenerator {

	public String getClassAnnotations(ClassDefinition classDefinition, Set<Annotation> annotationsUsed);
	
	public String getMemberAnnotations(IMemberDefinition memberDefinition, ClassDefinition classDefinition, Set<Annotation> annotationsUsed);

}
