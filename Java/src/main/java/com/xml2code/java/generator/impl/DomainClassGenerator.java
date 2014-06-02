package com.xml2code.java.generator.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.xml2code.core.definition.ClassDefinition;
import com.xml2code.core.definition.IMemberDefinition;
import com.xml2code.core.definition.InstructionsDefinition;
import com.xml2code.core.definition.ListDefinition;
import com.xml2code.core.definition.ProjectDefinition;
import com.xml2code.core.exception.UnsupportedFieldTypeException;
import com.xml2code.core.generator.ReplacementInstruction;
import com.xml2code.core.generator.Replacer;
import com.xml2code.core.util.StringConstants;
import com.xml2code.java.exception.JavaProjectCreationFailedException;
import com.xml2code.java.factory.GeneratorFactory;
import com.xml2code.java.generator.ClassGenerator;
import com.xml2code.java.generator.IAnnotationGenerator;
import com.xml2code.java.generator.pattern.Pattern;
import com.xml2code.java.generator.replace.ReplacementInstructions;
import com.xml2code.java.util.TemplateUtil;

public class DomainClassGenerator extends ClassGenerator {

	@Override
	public void generateBaseClass(ProjectDefinition projectDefinition, String targetDir) 
			throws JavaProjectCreationFailedException {

		String domainPackage = getRootPackage(projectDefinition) + ".domain";
		String template = TemplateUtil.getJavaDomainObjectTemplate();
		String contentFilePath = targetDir + "/" + ClassDefinition.DEFAULT_SUPERCLASS + ".java";

		List<ReplacementInstruction> replacementInstructions = new ArrayList<ReplacementInstruction>();
		replacementInstructions.add(new ReplacementInstruction(Pattern.PACKAGE, domainPackage, false));

		writeOutFile(template, replacementInstructions, contentFilePath);
	}

	@Override
	protected void generateImplementationClass(ClassDefinition classDefinition, String targetDir, String rootPackage)
			throws JavaProjectCreationFailedException {

		try {

			String template = TemplateUtil.getJavaDomainObjectImplTemplate();
			String domainPackage = rootPackage + ".domain";
			String contentFilePath = targetDir + "/" + classDefinition.getClassName() + ".java";

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

			writeOutFile(template, replacementInstructions, contentFilePath);

		} catch (UnsupportedFieldTypeException ufte) {

			throw new JavaProjectCreationFailedException(ufte);

		}

	}

	protected String generateImports(InstructionsDefinition instructionsDefinition,
			ClassDefinition classDefinition) {

		StringBuffer imports = new StringBuffer();
		List<String> importStatements = getRequiredImportStatements(instructionsDefinition, classDefinition);

		for (String statement : importStatements) {

			imports.append(statement);

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
				lists.append(StringConstants.INDENT);
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

	private List<String> getRequiredImportStatements(InstructionsDefinition instructionsDefinition,
			ClassDefinition classDefinition) {

		Map<String, Set<String>> importStatements = new HashMap<String, Set<String>>();
		Set<String> javaUtil = getImportsForPackage(importStatements, "java.util");
		Set<String> javaShared = getImportsForPackage(importStatements, "com.xml2code.java.shared");
		Set<String> jackson = getImportsForPackage(importStatements, "org.codehaus.jackson");
		Set<String> javaMath = getImportsForPackage(importStatements, "java.math");
		Set<String> javax = getImportsForPackage(importStatements, "javax");

		if (classDefinition.getListDefinitions().size() > 0) {

			javaUtil.add("import java.util.List;");
			javaUtil.add("import java.util.ArrayList;");

		}

		if (classDefinition.hasDate()) {

			javaUtil.add("import java.util.Date;");

			if (instructionsDefinition.isGenerateApi()) {

				javaShared.add("import com.xml2code.java.shared.json.DateJsonDeserializer;");
				javaShared.add("import com.xml2code.java.shared.json.DateJsonSerializer;");

				jackson.add("import org.codehaus.jackson.map.annotate.JsonDeserialize;");
				jackson.add("import org.codehaus.jackson.map.annotate.JsonSerialize;");

			}

		}

		if (classDefinition.hasDateTime()) {

			javaUtil.add("import java.util.Date;");

			if (instructionsDefinition.isGenerateApi()) {

				javaShared.add("import com.xml2code.java.shared.json.DateTimeJsonDeserializer;");
				javaShared.add("import com.xml2code.java.shared.json.DateTimeJsonSerializer;");

				jackson.add("import org.codehaus.jackson.map.annotate.JsonDeserialize;");
				jackson.add("import org.codehaus.jackson.map.annotate.JsonSerialize;");

			}

		}

		if (classDefinition.hasDecimal()) {

			javaMath.add("import java.math.BigDecimal;");

		}

		if (instructionsDefinition.isGenerateRelationalMapping()) {

			javax.add("import javax.persistence.*;");

		}

		List<String> imports = new ArrayList<String>();
		Iterator<Set<String>> iterator = importStatements.values().iterator();
		while (iterator.hasNext()) {

			Iterator<String> importIterator = iterator.next().iterator();
			while (importIterator.hasNext()) {

				imports.add(importIterator.next());
				imports.add(StringConstants.NEW_LINE);

			}

			if (iterator.hasNext()) {

				imports.add(StringConstants.NEW_LINE);

			}

		}

		return imports;

	}

	private Set<String> getImportsForPackage(Map<String, Set<String>> importStatements, String pack) {

		Set<String> packImports = importStatements.get(pack);

		if (packImports == null) {

			packImports = new HashSet<String>();
			importStatements.put(pack, packImports);

		}

		return packImports;

	}

}
