package com.xml2code.java.generator.impl;

import java.util.ArrayList;
import java.util.List;

import com.xml2code.core.definition.ClassDefinition;
import com.xml2code.core.definition.ProjectDefinition;
import com.xml2code.core.generator.ReplacementInstruction;
import com.xml2code.java.exception.JavaProjectCreationFailedException;
import com.xml2code.java.generator.ClassGenerator;
import com.xml2code.java.generator.pattern.Pattern;
import com.xml2code.java.util.TemplateUtil;

public class ControllerClassGenerator extends ClassGenerator {

	public void generateBaseClass(ProjectDefinition projectDefinition, String targetDir)
			throws JavaProjectCreationFailedException {
		
		String template = TemplateUtil.getJavaControllerTemplate();
		String rootPackage = getRootPackage(projectDefinition);
		String contentFilePath = targetDir + "/" + ClassDefinition.DEFAULT_SUPERCLASS + "Controller.java";
		
		List<ReplacementInstruction> replacementInstructions = new ArrayList<ReplacementInstruction>();
		replacementInstructions.add(new ReplacementInstruction(Pattern.APP, rootPackage, true));
		
		writeOutFile(template, replacementInstructions, contentFilePath);

	}
	
	protected void generateImplementationClass(ClassDefinition classDefinition, String targetDir, String rootPackage) 
			throws JavaProjectCreationFailedException {
		
		String template = TemplateUtil.getJavaControllerImplTemplate();
		String contentFilePath = targetDir + "/" + classDefinition.getClassName() + "Controller.java";
		
		List<ReplacementInstruction> replacementInstructions = new ArrayList<ReplacementInstruction>();
		replacementInstructions.add(new ReplacementInstruction(Pattern.APP, rootPackage, true));
		replacementInstructions.add(new ReplacementInstruction(Pattern.CLASS_NAME, classDefinition.getClassName(), true));
		
		writeOutFile(template, replacementInstructions, contentFilePath);
		
	}

}
