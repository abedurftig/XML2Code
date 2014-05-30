package com.xml2code.java.generator.replace;

import java.util.ArrayList;
import java.util.List;

import com.xml2code.core.definition.ClassDefinition;
import com.xml2code.core.definition.FieldDefinition;
import com.xml2code.core.definition.ReferenceDefinition;
import com.xml2code.core.exception.UnsupportedFieldTypeException;
import com.xml2code.core.generator.ReplacementInstruction;
import com.xml2code.core.types.FieldType;
import com.xml2code.java.generator.pattern.Pattern;

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

		return replacementInstructions;
		
	}

	public static List<ReplacementInstruction> getFieldInstructions(
			FieldDefinition fieldDefinition, String annotations) throws UnsupportedFieldTypeException {

		List<ReplacementInstruction> replacementInstructions = new ArrayList<ReplacementInstruction>();
		replacementInstructions.add(new ReplacementInstruction(Pattern.NAME, fieldDefinition.getFieldName(), true));
		replacementInstructions.add(new ReplacementInstruction(Pattern.TYPE, FieldType.getType(fieldDefinition), true));
		replacementInstructions.add(new ReplacementInstruction(Pattern.ANNOTATION, annotations, true));

		return replacementInstructions;

	}

	public static List<ReplacementInstruction> getReferenceInstructions(
			ReferenceDefinition referenceDefinition, String annotations) {

		List<ReplacementInstruction> replacementInstructions = new ArrayList<ReplacementInstruction>();
		replacementInstructions.add(new ReplacementInstruction(Pattern.NAME, referenceDefinition.getReferenceName(), true));
		replacementInstructions.add(new ReplacementInstruction(Pattern.TYPE, referenceDefinition.getReferenceType(), true));
		replacementInstructions.add(new ReplacementInstruction(Pattern.ANNOTATION, annotations, true));

		return replacementInstructions;

	}
	
}
