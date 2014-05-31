package com.xml2code.java.generator.replace;

import java.util.ArrayList;
import java.util.List;

import com.xml2code.core.definition.ClassDefinition;
import com.xml2code.core.definition.IMemberDefinition;
import com.xml2code.core.definition.ListDefinition;
import com.xml2code.core.generator.ReplacementInstruction;
import com.xml2code.java.generator.pattern.Pattern;
import com.xml2code.java.util.NameUtil;

public class ReplacementInstructions {

	public static List<ReplacementInstruction> getDomainClassInstructions(
			ClassDefinition classDefinition, String domainPackage, String importStatements, String annotations,
			String fields, String references, String lists, String constructor, String gettersAndSetters) {
		
		List<ReplacementInstruction> replacementInstructions = new ArrayList<ReplacementInstruction>();
		replacementInstructions.add(new ReplacementInstruction(Pattern.PACKAGE, domainPackage, false));
		replacementInstructions.add(new ReplacementInstruction(Pattern.CLASS_NAME, classDefinition.getClassName(), false));
		replacementInstructions.add(new ReplacementInstruction(Pattern.SUPER, classDefinition.getSuperClassName(), false));
		replacementInstructions.add(new ReplacementInstruction(Pattern.CLASS_ABSTRACT, classDefinition.isSuperClass() ? " abstract " : " ", false));
		replacementInstructions.add(new ReplacementInstruction(Pattern.DESC, classDefinition.getClassDescription(), false));
		replacementInstructions.add(new ReplacementInstruction(Pattern.ANNOTATION, annotations, false));
		replacementInstructions.add(new ReplacementInstruction(Pattern.IMPORTS, importStatements, false));
		replacementInstructions.add(new ReplacementInstruction(Pattern.FIELDS, fields, false));
		replacementInstructions.add(new ReplacementInstruction(Pattern.REFERENCES, references, false));
		replacementInstructions.add(new ReplacementInstruction(Pattern.LISTS, lists, false));
		replacementInstructions.add(new ReplacementInstruction(Pattern.CONSTRUCTOR, constructor, false));
		replacementInstructions.add(new ReplacementInstruction(Pattern.MODIFIERS, gettersAndSetters, false));

		return replacementInstructions;
		
	}
	
	// ------------------------------------------
	// Members
	// ------------------------------------------
	
	public static List<ReplacementInstruction> getMemberInstructions(
			IMemberDefinition memberDefinition, String annotations) {

		List<ReplacementInstruction> replacementInstructions = new ArrayList<ReplacementInstruction>();
		replacementInstructions.add(new ReplacementInstruction(Pattern.NAME, memberDefinition.getName(), true));
		replacementInstructions.add(new ReplacementInstruction(Pattern.TYPE, memberDefinition.getType(), true));
		replacementInstructions.add(new ReplacementInstruction(Pattern.ANNOTATION, annotations, true));

		return replacementInstructions;

	}

	public static List<ReplacementInstruction> getMemberGetterAndSetterInstructions(IMemberDefinition memberDefinition) {

		List<ReplacementInstruction> replacementInstructions = new ArrayList<ReplacementInstruction>();
		replacementInstructions.add(new ReplacementInstruction(Pattern.NAME, memberDefinition.getName(), true));
		replacementInstructions.add(new ReplacementInstruction(Pattern.TYPE, memberDefinition.getType(), true));
		replacementInstructions.add(new ReplacementInstruction(Pattern.GETTER, NameUtil.getGetterName(memberDefinition), true));
		replacementInstructions.add(new ReplacementInstruction(Pattern.SETTER, NameUtil.getSetterName(memberDefinition), true));

		return replacementInstructions;

	}
	
	// ------------------------------------------
	// Constructor
	// ------------------------------------------
	
	public static List<ReplacementInstruction> getConstructorInstructions(ClassDefinition classDefinition, String lists) {

		List<ReplacementInstruction> replacementInstructions = new ArrayList<ReplacementInstruction>();
		replacementInstructions.add(new ReplacementInstruction(Pattern.CLASS_NAME, classDefinition.getClassName(), false));
		replacementInstructions.add(new ReplacementInstruction(Pattern.INIT_LISTS, lists, false));

		return replacementInstructions;

	}
	
	public static List<ReplacementInstruction> getInitListInstructions(ListDefinition listDefinition) {

		List<ReplacementInstruction> replacementInstructions = new ArrayList<ReplacementInstruction>();
		replacementInstructions.add(new ReplacementInstruction(Pattern.TYPE, listDefinition.getType(), false));
		replacementInstructions.add(new ReplacementInstruction(Pattern.NAME, listDefinition.getName(), false));

		return replacementInstructions;

	}
	
}
