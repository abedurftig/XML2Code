package com.xml2code.java.generator.impl;

import com.xml2code.core.definition.ClassDefinition;
import com.xml2code.core.definition.FieldDefinition;
import com.xml2code.core.definition.InstructionsDefinition;
import com.xml2code.core.definition.ProjectDefinition;
import com.xml2code.core.exception.UnsupportedFieldTypeException;
import com.xml2code.core.generator.ReplacementInstruction;
import com.xml2code.core.generator.Replacer;
import com.xml2code.core.types.FieldType;
import com.xml2code.core.util.ResourceUtil;
import com.xml2code.core.util.StringConstants;
import com.xml2code.java.exception.JavaProjectCreationFailedException;
import com.xml2code.java.factory.GeneratorFactory;
import com.xml2code.java.generator.IAnnotationGenerator;
import com.xml2code.java.generator.IDomainClassGenerator;
import com.xml2code.java.generator.pattern.Pattern;
import com.xml2code.java.generator.replace.ReplacementInstructions;
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

		String domainPackage = "com." + projectDef.getProjectName().toLowerCase() + ".domain";

		for (ClassDefinition classDefinition : projectDef.getClassDefinitions()) {

			generateDomainObjectClass(classDefinition, domainPath, domainPackage);

		}
		
	}

	private void generateDomainObjectClass(ClassDefinition classDefinition, String domainPath, String domainPackage)
			throws JavaProjectCreationFailedException {

		String content = TemplateUtil.getJavaDomainObjectImplTemplate();

		String importStatements = generateImports(classDefinition.getProjectDefinition().getInstructions(), classDefinition);

		List<ReplacementInstruction> replacementInstructions = 
				ReplacementInstructions.getDomainClassInstructions(classDefinition, domainPackage, importStatements);

		content = Replacer.replace(content, replacementInstructions);

		File contentFile = new File(domainPath + "/" + classDefinition.getClassName() + ".java");

		try {

			ResourceUtil.writeContentToFile(contentFile, content, ResourceUtil.FILE_TYPE_JAVA);

		} catch (IOException ioe) {

			throw new JavaProjectCreationFailedException(ioe);

		}

	}

	private String generateImports(InstructionsDefinition instructionsDefinition,
									 ClassDefinition classDefinition) {

		StringBuffer imports = new StringBuffer();
		List<String> importStatements = getRequiredImportStatements(instructionsDefinition, classDefinition);

		for (String statement : importStatements) {

			imports.append(statement + StringConstants.NEW_LINE);

		}

		return imports.toString();

	}

	private List<String> getRequiredImportStatements(InstructionsDefinition instructionsDefinition,
													 ClassDefinition classDefinition) {

		List<String> importStatements = new ArrayList<String>();

		if (classDefinition.getListDefinitions().size() > 0) {

			importStatements.add("import java.util.List;");
			importStatements.add("import java.util.ArrayList;");

		}

		if (classDefinition.hasDate()) {

			importStatements.add("import java.util.Date;");

		}

		if (classDefinition.hasDecimal()) {

			importStatements.add("import java.math.BigDecimal;");

		}

		if (instructionsDefinition.isGenerateRelationalMapping()) {

			importStatements.add("import javax.persistence.*;");

		}

		return importStatements;

	}

	protected String generateFields(ClassDefinition classDefinition)
			throws UnsupportedFieldTypeException {

		IAnnotationGenerator annotationGenerator = GeneratorFactory.getAnnotationGenerator();

		StringBuffer fields = new StringBuffer();

		for (int i = 0; i < classDefinition.getFieldDefinitions().size(); i++) {

			if (i > 0) {
				fields.append(StringConstants.NEW_LINE);
			}

			FieldDefinition fieldDefinition = classDefinition.getFieldDefinitions().get(i);

			String code = TemplateUtil.getJavaPartialFieldTemplate();
			code = code.replaceAll(Pattern.NAME, fieldDefinition.getFieldName());
			code = code.replaceAll(Pattern.TYPE, FieldType.getType(fieldDefinition));

			String annotations = annotationGenerator.getFieldAnnotations(fieldDefinition);
			String annotationsPattern = Pattern.ANNOTATION + (annotations.isEmpty() ? "\t" : "");
			code = code.replaceAll(annotationsPattern, annotations);

			fields.append(code);

		}

		return fields.toString();

	}

}
