package com.xml2code.java.util;

import java.util.Iterator;
import java.util.Set;

import com.xml2code.java.annotation.Annotation;

public class AnnotationUtil {

	public static <T extends Annotation> boolean containsAnnotationClass(Class<T> annotationClass, Set<Annotation> annotations) {
		
		Iterator<Annotation> iterator = annotations.iterator();
		while (iterator.hasNext()) {
			
			if (iterator.next().getClass().equals(annotationClass)) {
				
				return true;
				
			}
			
		}
		
		return false;
		
	}
	
}
