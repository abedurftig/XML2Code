package com.xml2code.java.factory;

import com.xml2code.java.generator.IAnnotationGenerator;
import com.xml2code.java.generator.IDomainClassGenerator;
import com.xml2code.java.generator.impl.AnnotationGenerator;
import com.xml2code.java.generator.impl.DomainClassGenerator;

public class GeneratorFactory {

	public static IDomainClassGenerator getDomainClassGenerator() {
		return new DomainClassGenerator();
	}

	public static IAnnotationGenerator getAnnotationGenerator() {
		return new AnnotationGenerator();
	}
}
