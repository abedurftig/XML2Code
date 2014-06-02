package com.xml2code.java.factory;

import com.xml2code.java.generator.ClassGenerator;
import com.xml2code.java.generator.IAnnotationGenerator;
import com.xml2code.java.generator.IResourceFileGenerator;
import com.xml2code.java.generator.impl.AnnotationGenerator;
import com.xml2code.java.generator.impl.ControllerClassGenerator;
import com.xml2code.java.generator.impl.DomainClassGenerator;
import com.xml2code.java.generator.impl.JsonRestApiGenerator;

public class GeneratorFactory {

	private static ClassGenerator domainClassGenerator = null;
	
	private static ClassGenerator controllerClassGenerator = null;
	
	private static ClassGenerator jsonRestApiGenerator = null;
	
	private static IAnnotationGenerator annotationGenerator = null;
	
	private static IResourceFileGenerator resourceFileGenerator = null;
	
	public static ClassGenerator getDomainClassGenerator() {
		
		if (domainClassGenerator == null) {
			
			domainClassGenerator = new DomainClassGenerator();
			
		}
		
		return domainClassGenerator;
		
	}

	public static ClassGenerator getControllerClassGenerator() {
		
		if (controllerClassGenerator == null) {
			
			controllerClassGenerator = new ControllerClassGenerator();
			
		}
		
		return controllerClassGenerator;
		
	}
	
	public static ClassGenerator getJsonRestApiGenerator() {
		
		if (jsonRestApiGenerator == null) {
			
			jsonRestApiGenerator = new JsonRestApiGenerator();
			
		}
		
		return jsonRestApiGenerator;
		
	}
	
	public static IAnnotationGenerator getAnnotationGenerator() {
		
		if (annotationGenerator == null) {
			
			annotationGenerator = new AnnotationGenerator();
			
		}
		
		return annotationGenerator;
		
	}
	
	public static IResourceFileGenerator getResourceFileGenerator() {
		
		if (resourceFileGenerator == null) {
			
			//resourceFileGenerator = new ResourceFileGenerator();
			
		}
		
		return resourceFileGenerator;
		
	}
}
