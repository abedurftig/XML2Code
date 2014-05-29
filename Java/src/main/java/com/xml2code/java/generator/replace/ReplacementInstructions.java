package com.xml2code.java.generator.replace;

import java.util.ArrayList;
import java.util.List;

import com.xml2code.core.definition.ClassDefinition;
import com.xml2code.core.generator.ReplacementInstruction;
import com.xml2code.java.generator.pattern.Pattern;

public class ReplacementInstructions {

	public static List<ReplacementInstruction> getDomainClassInstructions(ClassDefinition classDefinition, String domainPackage, String importStatements) {
		
		List<ReplacementInstruction> replacementInstructions = new ArrayList<ReplacementInstruction>();
		replacementInstructions.add(new ReplacementInstruction(Pattern.PACKAGE, domainPackage, false));
		replacementInstructions.add(new ReplacementInstruction(Pattern.CLASS_NAME, classDefinition.getClassName(), false));
		replacementInstructions.add(new ReplacementInstruction(Pattern.SUPER, classDefinition.getSuperClassName(), false));
		replacementInstructions.add(new ReplacementInstruction(Pattern.CLASS_ABSTRACT, classDefinition.isSuperClass() ? " abstract " : " ", false));
		replacementInstructions.add(new ReplacementInstruction(Pattern.DESC, classDefinition.getClassDescription(), false));
		replacementInstructions.add(new ReplacementInstruction(Pattern.IMPORTS, importStatements, false));
		
		return replacementInstructions;
		
	}
	
}
