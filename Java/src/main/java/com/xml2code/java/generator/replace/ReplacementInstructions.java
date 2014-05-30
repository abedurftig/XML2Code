package com.xml2code.java.generator.replace;

import java.util.ArrayList;
import java.util.List;

import com.xml2code.core.definition.ClassDefinition;
import com.xml2code.core.definition.FieldDefinition;
import com.xml2code.core.definition.ListDefinition;
import com.xml2code.core.definition.ReferenceDefinition;
import com.xml2code.core.exception.UnsupportedFieldTypeException;
import com.xml2code.core.generator.ReplacementInstruction;
import com.xml2code.core.types.FieldType;
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
	// Fields
	// ------------------------------------------

	public static List<ReplacementInstruction> getFieldInstructions(
			FieldDefinition fieldDefinition, String annotations) throws UnsupportedFieldTypeException {

		List<ReplacementInstruction> replacementInstructions = new ArrayList<ReplacementInstruction>();
		replacementInstructions.add(new ReplacementInstruction(Pattern.NAME, fieldDefinition.getFieldName(), true));
		replacementInstructions.add(new ReplacementInstruction(Pattern.TYPE, FieldType.getType(fieldDefinition), true));
		replacementInstructions.add(new ReplacementInstruction(Pattern.ANNOTATION, annotations, true));

		return replacementInstructions;

	}

	public static List<ReplacementInstruction> getFieldModifiersInstructions(FieldDefinition fieldDefinition) {

		List<ReplacementInstruction> replacementInstructions = new ArrayList<ReplacementInstruction>();
		replacementInstructions.add(new ReplacementInstruction(Pattern.NAME, fieldDefinition.getFieldName(), true));
		replacementInstructions.add(new ReplacementInstruction(Pattern.TYPE, fieldDefinition.getFieldName(), true));
		replacementInstructions.add(new ReplacementInstruction(Pattern.GETTER, NameUtil.getGetterName(fieldDefinition), true));
		replacementInstructions.add(new ReplacementInstruction(Pattern.SETTER, NameUtil.getSetterName(fieldDefinition), true));

		return replacementInstructions;

	}

	// ------------------------------------------
	// Lists
	// ------------------------------------------

	public static List<ReplacementInstruction> getListModifiersInstructions(ListDefinition listDefinition) {

		List<ReplacementInstruction> replacementInstructions = new ArrayList<ReplacementInstruction>();
		replacementInstructions.add(new ReplacementInstruction(Pattern.NAME, listDefinition.getListName(), true));
		replacementInstructions.add(new ReplacementInstruction(Pattern.TYPE, "List<" + listDefinition.getListItemType() + ">", true));
		replacementInstructions.add(new ReplacementInstruction(Pattern.GETTER, NameUtil.getGetterName(listDefinition), true));
		replacementInstructions.add(new ReplacementInstruction(Pattern.SETTER, NameUtil.getSetterName(listDefinition), true));

		return replacementInstructions;

	}

	// ------------------------------------------
	// References
	// ------------------------------------------

	public static List<ReplacementInstruction> getReferenceInstructions(
			ReferenceDefinition referenceDefinition, String annotations) {

		List<ReplacementInstruction> replacementInstructions = new ArrayList<ReplacementInstruction>();
		replacementInstructions.add(new ReplacementInstruction(Pattern.NAME, referenceDefinition.getReferenceName(), true));
		replacementInstructions.add(new ReplacementInstruction(Pattern.TYPE, referenceDefinition.getReferenceType(), true));
		replacementInstructions.add(new ReplacementInstruction(Pattern.ANNOTATION, annotations, true));

		return replacementInstructions;

	}

	public static List<ReplacementInstruction> getReferenceModifiersInstructions(ReferenceDefinition referenceDefinition) {

		List<ReplacementInstruction> replacementInstructions = new ArrayList<ReplacementInstruction>();
		replacementInstructions.add(new ReplacementInstruction(Pattern.NAME, referenceDefinition.getReferenceName(), true));
		replacementInstructions.add(new ReplacementInstruction(Pattern.TYPE, referenceDefinition.getReferenceType(), true));
		replacementInstructions.add(new ReplacementInstruction(Pattern.GETTER, NameUtil.getGetterName(referenceDefinition), true));
		replacementInstructions.add(new ReplacementInstruction(Pattern.SETTER, NameUtil.getSetterName(referenceDefinition), true));

		return replacementInstructions;

	}
	
}
