package com.xml2code.java.generator.impl;

import com.xml2code.core.definition.*;
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
import java.util.Iterator;
import java.util.List;

public class DomainClassGenerator implements IDomainClassGenerator {

	public void generateDomainObjectBaseClass(ProjectDefinition projectDef, String domainPath)
			throws JavaProjectCreationFailedException {

		String domainPackage = getDomainPackage(projectDef);

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

		String domainPackage = getDomainPackage(projectDef);

		for (ClassDefinition classDefinition : projectDef.getClassDefinitions()) {

			generateDomainObjectClass(classDefinition, domainPath, domainPackage);

		}
		
	}

	private void generateDomainObjectClass(ClassDefinition classDefinition, String domainPath, String domainPackage)
			throws JavaProjectCreationFailedException {

		try {

			String content = TemplateUtil.getJavaDomainObjectImplTemplate();
			String importStatements = generateImports(classDefinition.getProjectDefinition().getInstructions(), classDefinition);
			String annotations = generateClassAnnotations(classDefinition);
			String fields = generateFields(classDefinition);
			String references = generateReferences(classDefinition);
			String lists = generateLists(classDefinition);
			String constructor = generateConstructor(classDefinition);
			String gettersAndSetters = generateGettersAndSetters(classDefinition);

			List<ReplacementInstruction> replacementInstructions = ReplacementInstructions.getDomainClassInstructions(
					classDefinition, domainPackage, importStatements, annotations, fields, references,
					lists, constructor, gettersAndSetters);

			content = Replacer.replace(content, replacementInstructions);
			File contentFile = new File(domainPath + "/" + classDefinition.getClassName() + ".java");
			ResourceUtil.writeContentToFile(contentFile, content, ResourceUtil.FILE_TYPE_JAVA);

		} catch (IOException ioe) {

			throw new JavaProjectCreationFailedException(ioe);

		} catch (UnsupportedFieldTypeException ufte) {

			throw new JavaProjectCreationFailedException(ufte);

		}

	}

	protected String generateImports(InstructionsDefinition instructionsDefinition,
									 ClassDefinition classDefinition) {

		StringBuffer imports = new StringBuffer();
		List<String> importStatements = getRequiredImportStatements(instructionsDefinition, classDefinition);

		for (String statement : importStatements) {

			imports.append(statement + StringConstants.NEW_LINE);

		}

		return imports.toString();

	}

	protected String generateClassAnnotations(ClassDefinition classDefinition) {

		// TODO: finish implementation
		return "";

	}

	protected String generateLists(ClassDefinition classDefinition) {

		// TODO: finish implementation
		return "";

	}

	protected String generateConstructor(ClassDefinition classDefinition) {

		// TODO: finish implementation
		return "";

	}

	protected String generateGettersAndSetters(ClassDefinition classDefinition) {

		// TODO: finish implementation
		return "";

	}

	protected String generateFields(ClassDefinition classDefinition) throws UnsupportedFieldTypeException {

		IAnnotationGenerator annotationGenerator = GeneratorFactory.getAnnotationGenerator();

		StringBuffer output = new StringBuffer();

		FieldDefinition fieldDefinition = null;
		String code = "";
		String annotations = "";
		List<ReplacementInstruction> replacementInstructions = null;

		Iterator<FieldDefinition> iterator = classDefinition.getFieldDefinitions().iterator();
		while (iterator.hasNext()) {

			fieldDefinition = iterator.next();
			code = TemplateUtil.getJavaPartialFieldTemplate();
			annotations = annotationGenerator.getFieldAnnotations(fieldDefinition);

			replacementInstructions = ReplacementInstructions.getFieldInstructions(fieldDefinition, annotations);

			code = Replacer.replace(code, replacementInstructions);

			output.append(code);

		}

		return output.toString();

	}

	protected String generateReferences(ClassDefinition classDefinition) {

		IAnnotationGenerator annotationGenerator = GeneratorFactory.getAnnotationGenerator();

		StringBuffer output = new StringBuffer();

		ReferenceDefinition referenceDefinition = null;
		String code = "";
		String annotations = "";
		List<ReplacementInstruction> replacementInstructions = null;

		Iterator<ReferenceDefinition> iterator = classDefinition.getReferenceDefinitions().iterator();
		while (iterator.hasNext()) {

			referenceDefinition = iterator.next();
			code = TemplateUtil.getJavaPartialReferenceTemplate();
			annotations = annotationGenerator.getReferenceAnnotations(referenceDefinition);

			replacementInstructions = ReplacementInstructions.getReferenceInstructions(referenceDefinition, annotations);

			code = Replacer.replace(code, replacementInstructions);

			output.append(code);

		}

		return output.toString();

	}

	private String getDomainPackage(ProjectDefinition projectDefinition) {

		return "com." + projectDefinition.getProjectName().toLowerCase() + ".domain";

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

}
