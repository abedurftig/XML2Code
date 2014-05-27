package com.xml2code.java.generator.impl;

import com.xml2code.core.definition.ClassDefinition;
import com.xml2code.core.definition.ProjectDefinition;
import com.xml2code.core.generator.ReplacementInstruction;
import com.xml2code.core.generator.Replacer;
import com.xml2code.core.util.ResourceUtil;
import com.xml2code.java.exception.JavaProjectCreationFailedException;
import com.xml2code.java.generator.IDomainClassGenerator;
import com.xml2code.java.generator.pattern.Pattern;
import com.xml2code.java.util.TemplateUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DomainClassGenerator implements IDomainClassGenerator {

	public void generateDomainObjectBaseClass(ProjectDefinition projectDef, String domainPath)
			throws JavaProjectCreationFailedException {

		String domainPackage = "com." + projectDef.getProjectName().toLowerCase() + ".domain";

		String content = TemplateUtil.getJavaDomainObjectTemplate();

		List<ReplacementInstruction> replacementInstructions = new ArrayList<ReplacementInstruction>();
		replacementInstructions.add(new ReplacementInstruction(Pattern.PACKAGE, domainPackage, false));

		content = Replacer.replace(content, replacementInstructions);

		File contentFile = new File(domainPath + "/" + ClassDefinition.DEFAULT_SUPERCLASS + ".java");

		try {

			ResourceUtil.writeContentToFile(contentFile, content, ResourceUtil.FILE_TYPE_JAVA);

		} catch (IOException ioe) {

			throw new JavaProjectCreationFailedException(ioe);

		}

	}

	public void generateDomainObjectClasses(ProjectDefinition projectDef, String domainPath)
			throws JavaProjectCreationFailedException {
		// TODO Auto-generated method stub
		
	}

}
