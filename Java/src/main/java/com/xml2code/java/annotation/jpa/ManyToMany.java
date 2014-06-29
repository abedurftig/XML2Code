package com.xml2code.java.annotation.jpa;

import org.apache.commons.lang.StringUtils;

import com.xml2code.core.definition.ClassDefinition;
import com.xml2code.core.definition.ListDefinition;
import com.xml2code.core.util.StringConstants;
import com.xml2code.core.util.StringUtil;
import com.xml2code.java.annotation.ListAnnotation;
import com.xml2code.java.util.TemplateUtil;

public class ManyToMany extends JpaAnnotation implements ListAnnotation {

	private ListDefinition listDefinition;
	private ClassDefinition classDefinition;

	public ManyToMany(ListDefinition listDefinition, ClassDefinition classDefinition) {
		this.listDefinition = listDefinition;
		this.classDefinition = classDefinition;
	}

	@Override
	public String getCode() {

		String template = "";

		if (this.listDefinition.isOwner()) {

			template = TemplateUtil.getJavaPartialManyToManyOwnerTemplate();
			template = template.replace("{{CLASS}}", listDefinition.getType());
			template = template.replace("{{JOIN_TABLE_NAME}}", getJoinTableName());
			template = template.replace("{{OWNER_ID}}", StringUtil.joinWithUnderscore(classDefinition.getClassName()) + "_ID");
			template = template.replace("{{OWNED_ID}}", StringUtil.joinWithUnderscore(listDefinition.getType()) + "_ID");

		} else {

			template = TemplateUtil.getJavaPartialManyToManyOwnedTemplate();
			template = template.replace("{{CLASS}}", listDefinition.getType());
			template = template.replace("{{MAPPED_BY}}", StringUtil.getPlural(this.classDefinition.getClassName()).toLowerCase());

		}
		
		// TODO: cannot explain yet where the additional new line is coming from
		return StringUtils.removeEnd(template, StringConstants.NEW_LINE);

	}

	public ListDefinition getListDefinition() {
		return this.listDefinition;
	}

	private String getJoinTableName() {

		String tableName = StringUtil.joinWithUnderscore(this.classDefinition.getClassName()) + "_" + 
				StringUtil.getPlural(this.listDefinition.getType());

		return tableName.toUpperCase();

	}

}
