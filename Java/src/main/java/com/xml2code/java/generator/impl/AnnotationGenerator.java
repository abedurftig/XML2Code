package com.xml2code.java.generator.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.xml2code.core.definition.ClassDefinition;
import com.xml2code.core.definition.FieldDefinition;
import com.xml2code.core.definition.IMemberDefinition;
import com.xml2code.core.definition.ListDefinition;
import com.xml2code.core.definition.ProjectDefinition;
import com.xml2code.core.definition.ReferenceDefinition;
import com.xml2code.core.types.FieldType;
import com.xml2code.core.types.RelationshipType;
import com.xml2code.core.util.StringConstants;
import com.xml2code.java.annotation.Annotation;
import com.xml2code.java.annotation.ReferenceAnnotation;
import com.xml2code.java.annotation.jackson.JsonDateDeserialize;
import com.xml2code.java.annotation.jackson.JsonDateSerialize;
import com.xml2code.java.annotation.jackson.JsonIgnore;
import com.xml2code.java.annotation.jpa.Column;
import com.xml2code.java.annotation.jpa.Entity;
import com.xml2code.java.annotation.jpa.ManyToMany;
import com.xml2code.java.annotation.jpa.ManyToOne;
import com.xml2code.java.annotation.jpa.OneToMany;
import com.xml2code.java.annotation.jpa.OneToOne;
import com.xml2code.java.annotation.jpa.OneToOneOwned;
import com.xml2code.java.annotation.jpa.OneToOneOwner;
import com.xml2code.java.generator.IAnnotationGenerator;

public class AnnotationGenerator implements IAnnotationGenerator {

	public String getClassAnnotations(ClassDefinition classDefinition) {

		StringBuffer output = new StringBuffer();

		output.append(new Entity(classDefinition).getCode());

		return output.toString();

	}

	public String getMemberAnnotations(IMemberDefinition memberDefinition, ClassDefinition classDefinition) {

		if (memberDefinition instanceof FieldDefinition) {

			return getFieldAnnotations((FieldDefinition) memberDefinition);

		} else if (memberDefinition instanceof ReferenceDefinition) {

			return getReferenceAnnotations((ReferenceDefinition) memberDefinition, classDefinition);

		} else if (memberDefinition instanceof ListDefinition) {

			return getListAnnotations((ListDefinition) memberDefinition, classDefinition);

		}

		return "";

	}

	private String getFieldAnnotations(FieldDefinition fieldDefinition) {

		StringBuffer output = new StringBuffer();

		if (fieldDefinition.getFieldType() == FieldType.date ||
				fieldDefinition.getFieldType() == FieldType.datetime) {

			output.append(new JsonDateSerialize(fieldDefinition).getCode() + StringConstants.NEW_LINE_INDENT);
			output.append(new JsonDateDeserialize(fieldDefinition).getCode() + StringConstants.NEW_LINE_INDENT);

		}

		output.append(new Column(fieldDefinition).getCode());

		return output.toString();

	}

	private String getReferenceAnnotations(ReferenceDefinition referenceDefinition, ClassDefinition classDefinition) {

		StringBuffer output = new StringBuffer();

		List<ReferenceAnnotation> annotations = new ArrayList<ReferenceAnnotation>();

		// JSON annotations
		if ((referenceDefinition.getRelationshipType() == RelationshipType.oneToOne ||
				referenceDefinition.getRelationshipType() == RelationshipType.manyToOne) &&
				!referenceDefinition.isOwner()) {

			annotations.add(new JsonIgnore(referenceDefinition));

		}

		// JPA annotations
		if (referenceDefinition.getRelationshipType() == RelationshipType.oneToOne) {

			if (referenceDefinition.isUnidirectional()) {

				annotations.add(new OneToOne(referenceDefinition));

			} else {

				if (referenceDefinition.isOwner()) {

					ProjectDefinition projectDefinition = classDefinition.getProjectDefinition();

					ClassDefinition ownedEntity = projectDefinition.getClassDefinitionByName(referenceDefinition.getType());
					ReferenceDefinition backRef = ownedEntity.getReferenceOfType(classDefinition.getClassName());
					String mappedBy = backRef.getName();

					annotations.add(new OneToOneOwner(referenceDefinition, mappedBy));

				} else {

					annotations.add(new OneToOneOwned(referenceDefinition));

				}

			}

		} else if (referenceDefinition.getRelationshipType() == RelationshipType.manyToOne) {

			annotations.add(new ManyToOne(referenceDefinition));

		}

		Iterator<ReferenceAnnotation> iterator = annotations.iterator();
		while (iterator.hasNext()) {

			output.append(((Annotation) iterator.next()).getCode());
			if (iterator.hasNext()) {
				output.append(StringConstants.NEW_LINE + StringConstants.INDENT);
			}
		}

		return output.toString();

	}

	private String getListAnnotations(ListDefinition listDefinition, ClassDefinition classDefinition) {

		StringBuffer output = new StringBuffer();

		if (listDefinition.getRelationshipType() == RelationshipType.oneToMany) {

			ProjectDefinition projectDefinition = classDefinition.getProjectDefinition();

			ClassDefinition ownedEntity = projectDefinition.getClassDefinitionByName(listDefinition.getType());

			ReferenceDefinition backRef = ownedEntity.getReferenceOfType(classDefinition.getClassName());
			String mappedBy = backRef.getName();

			output.append(new OneToMany(listDefinition, mappedBy).getCode());

		}

		if (listDefinition.getRelationshipType() == RelationshipType.manyToMany) {

			output.append(new ManyToMany(listDefinition, classDefinition).getCode());

		}

		return output.toString();

	}

}
