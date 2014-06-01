package com.xml2code.java.generator.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.xml2code.core.definition.ClassDefinition;
import com.xml2code.core.definition.IMemberDefinition;
import com.xml2code.core.definition.InstructionsDefinition;
import com.xml2code.core.definition.ListDefinition;
import com.xml2code.core.definition.ProjectDefinition;
import com.xml2code.core.exception.UnsupportedFieldTypeException;
import com.xml2code.core.generator.ReplacementInstruction;
import com.xml2code.core.generator.Replacer;
import com.xml2code.core.util.ResourceUtil;
import com.xml2code.core.util.StringConstants;
import com.xml2code.java.exception.JavaProjectCreationFailedException;
import com.xml2code.java.factory.GeneratorFactory;
import com.xml2code.java.generator.IAnnotationGenerator;
import com.xml2code.java.generator.IDomainClassGenerator;
import com.xml2code.java.generator.pattern.Pattern;
import com.xml2code.java.generator.replace.ReplacementInstructions;
import com.xml2code.java.util.TemplateUtil;

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

	protected void generateDomainObjectClass(ClassDefinition classDefinition, String domainPath, String domainPackage)
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

		IAnnotationGenerator annotationGenerator = GeneratorFactory.getAnnotationGenerator();
		return annotationGenerator.getClassAnnotations(classDefinition);

	}

	protected String generateConstructor(ClassDefinition classDefinition) {

		StringBuffer lists = new StringBuffer(); 
		
		for (int i = 0; i < classDefinition.getListDefinitions().size(); i++) {
			
			if (i > 0) {
				lists.append("\t");
			}
			
			ListDefinition listDef = classDefinition.getListDefinitions().get(i);
			
			String initList = TemplateUtil.getJavaPartialInitListTemplate();
			List<ReplacementInstruction> listsReplacementInstructions = ReplacementInstructions.getInitListInstructions(listDef);
			initList = Replacer.replace(initList, listsReplacementInstructions);
			
			lists.append(initList);
		}
		
		String code = TemplateUtil.getJavaPartialConstructorTemplate();
		
		List<ReplacementInstruction> replacementInstructions = ReplacementInstructions.getConstructorInstructions(classDefinition, lists.toString());
		code = Replacer.replace(code, replacementInstructions);
		
		return code;

	}

	protected String generateGettersAndSetters(ClassDefinition classDefinition) {

		StringBuffer output = new StringBuffer();

		String fields = generateMemberGettersAndSetters(classDefinition.getFieldDefinitions());
		String references = generateMemberGettersAndSetters(classDefinition.getReferenceDefinitions());
		String lists = generateMemberGettersAndSetters(classDefinition.getListDefinitions());
		
		output.append(fields);
		output.append(references);
		output.append(lists);

		return output.toString();

	}

	protected String generateFields(ClassDefinition classDefinition) throws UnsupportedFieldTypeException {

		return generateMembers(classDefinition, classDefinition.getFieldDefinitions(), 
				TemplateUtil.getJavaPartialFieldTemplate());

	}

	protected String generateReferences(ClassDefinition classDefinition) {

		return generateMembers(classDefinition, classDefinition.getReferenceDefinitions(), 
				TemplateUtil.getJavaPartialReferenceTemplate());

	}
	
	protected String generateLists(ClassDefinition classDefinition) {

		return generateMembers(classDefinition, classDefinition.getListDefinitions(), 
				TemplateUtil.getJavaPartialListTemplate());

	}

	protected String generateMembers(ClassDefinition classDefinition, List<? extends IMemberDefinition> members, String template) {
		
		IAnnotationGenerator annotationGenerator = GeneratorFactory.getAnnotationGenerator();
		
		StringBuffer output = new StringBuffer();

		IMemberDefinition memberDefinition = null;
		String code = "";
		String annotations = "";
		List<ReplacementInstruction> replacementInstructions = null;

		Iterator<? extends IMemberDefinition> iterator = members.iterator();
		while (iterator.hasNext()) {

			memberDefinition = iterator.next();
			code = template;
			annotations = annotationGenerator.getMemberAnnotations(memberDefinition, classDefinition);
			replacementInstructions = ReplacementInstructions.getMemberInstructions(memberDefinition, annotations);
			code = Replacer.replace(code, replacementInstructions);
			output.append(code);

		}

		return output.toString();
		
	}
	
	protected String generateMemberGettersAndSetters(List<? extends IMemberDefinition> members) {
		
		StringBuffer output = new StringBuffer();
		String template = TemplateUtil.getJavaPartialGetterSetterTemplate();
		String code = "";
		
		for (int i = 0; i < members.size(); i++) {

			IMemberDefinition member = members.get(i);
			List<ReplacementInstruction> replacementInstructions =
					ReplacementInstructions.getMemberGetterAndSetterInstructions(member);
			code = Replacer.replace(template, replacementInstructions);
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
