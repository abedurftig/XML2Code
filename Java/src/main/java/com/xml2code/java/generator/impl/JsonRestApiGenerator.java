/**
 * 
 */
package com.xml2code.java.generator.impl;

import java.util.ArrayList;
import java.util.List;

import com.xml2code.core.definition.ClassDefinition;
import com.xml2code.core.definition.ProjectDefinition;
import com.xml2code.core.generator.ReplacementInstruction;
import com.xml2code.core.util.StringUtil;
import com.xml2code.java.exception.JavaProjectCreationFailedException;
import com.xml2code.java.generator.ClassGenerator;
import com.xml2code.java.generator.pattern.Pattern;
import com.xml2code.java.util.TemplateUtil;

/**
 * 
 */
public class JsonRestApiGenerator extends ClassGenerator {

	@Override
	public void generateBaseClass(ProjectDefinition projectDefinition, String targetDir) 
			throws JavaProjectCreationFailedException {
		
		String template = TemplateUtil.getJavaJsonRestApiTemplate();
		String rootPackage = getRootPackage(projectDefinition);
		String contentFilePath = targetDir + "/" + "RestAPI.java";
		
		List<ReplacementInstruction> replacementInstructions = new ArrayList<ReplacementInstruction>();
		replacementInstructions.add(new ReplacementInstruction(Pattern.APP, rootPackage, true));
		
		writeOutFile(template, replacementInstructions, contentFilePath);
		
	}

	@Override
	protected void generateImplementationClass(ClassDefinition classDefinition, String targetDir, String rootPackage)
			throws JavaProjectCreationFailedException {
		
		String template = TemplateUtil.getJavaJsonRestApiImplTemplate();
		String contentFilePath = targetDir + "/" + classDefinition.getClassName() + "API.java";
		
		List<ReplacementInstruction> replacementInstructions = new ArrayList<ReplacementInstruction>();
		replacementInstructions.add(new ReplacementInstruction(Pattern.APP, rootPackage, true));
		replacementInstructions.add(new ReplacementInstruction(Pattern.CLASS_NAME, classDefinition.getClassName(), true));
		replacementInstructions.add(new ReplacementInstruction(Pattern.CLASSES_NAME, StringUtil.getPlural(classDefinition.getClassName()), true));
		replacementInstructions.add(new ReplacementInstruction(Pattern.CLASS_INSTANCE, classDefinition.getClassName().toLowerCase(), true));
		replacementInstructions.add(new ReplacementInstruction(Pattern.CLASS_INSTANCES, StringUtil.getPlural(classDefinition.getClassName().toLowerCase()), true));
		
		writeOutFile(template, replacementInstructions, contentFilePath);
		
	}

}
