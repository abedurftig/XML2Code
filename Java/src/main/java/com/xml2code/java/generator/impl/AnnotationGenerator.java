package com.xml2code.java.generator.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

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

	public String getClassAnnotations(ClassDefinition classDefinition, Set<Annotation> annotationsUsed) {
		
		Set<Annotation> annotations = new HashSet<Annotation>();
		
		annotations.add(new Entity(classDefinition));
		
		annotationsUsed.addAll(annotations);
		return getAnnotationsCode(annotations);

	}

	public String getMemberAnnotations(IMemberDefinition memberDefinition, ClassDefinition classDefinition, Set<Annotation> annotationsUsed) {
		
		if (memberDefinition instanceof FieldDefinition) {

			return getFieldAnnotations((FieldDefinition) memberDefinition, annotationsUsed);

		} else if (memberDefinition instanceof ReferenceDefinition) {

			return getReferenceAnnotations((ReferenceDefinition) memberDefinition, classDefinition, annotationsUsed);

		} else if (memberDefinition instanceof ListDefinition) {

			return getListAnnotations((ListDefinition) memberDefinition, classDefinition, annotationsUsed);

		}

		return "";

	}

	private String getFieldAnnotations(FieldDefinition fieldDefinition, Set<Annotation> annotationsUsed) {

		Set<Annotation> annotations = new HashSet<Annotation>();
		
		if (fieldDefinition.getFieldType() == FieldType.date ||
				fieldDefinition.getFieldType() == FieldType.datetime) {

			annotations.add(new JsonDateSerialize(fieldDefinition));
			annotations.add(new JsonDateDeserialize(fieldDefinition));

		}

		annotations.add(new Column(fieldDefinition));
		
		annotationsUsed.addAll(annotations);
		return getAnnotationsCode(annotations);
		
	}

	private String getReferenceAnnotations(ReferenceDefinition referenceDefinition, ClassDefinition classDefinition, Set<Annotation> annotationsUsed) {

		Set<Annotation> annotations = new HashSet<Annotation>();

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

		annotationsUsed.addAll(annotations);
		return getAnnotationsCode(annotations);

	}

	private String getListAnnotations(ListDefinition listDefinition, ClassDefinition classDefinition, Set<Annotation> annotationsUsed) {

		Set<Annotation> annotations = new HashSet<Annotation>();
		
		if (listDefinition.getRelationshipType() == RelationshipType.oneToMany) {

			ProjectDefinition projectDefinition = classDefinition.getProjectDefinition();

			ClassDefinition ownedEntity = projectDefinition.getClassDefinitionByName(listDefinition.getType());

			ReferenceDefinition backRef = ownedEntity.getReferenceOfType(classDefinition.getClassName());
			String mappedBy = backRef.getName();

			annotations.add(new OneToMany(listDefinition, mappedBy));

		}

		if (listDefinition.getRelationshipType() == RelationshipType.manyToMany) {

			annotations.add(new ManyToMany(listDefinition, classDefinition));

		}

		annotationsUsed.addAll(annotations);
		return getAnnotationsCode(annotations);

	}
	
	private String getAnnotationsCode(Set<Annotation> annotations) {
		
		StringBuffer output = new StringBuffer();
		Iterator<Annotation> iterator = annotations.iterator();
		while (iterator.hasNext()) {

			output.append(((Annotation) iterator.next()).getCode());
			if (iterator.hasNext()) {
				output.append(StringConstants.NEW_LINE + StringConstants.INDENT);
			}
		}
		
		return output.toString();
		
	}

}
