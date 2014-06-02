package com.xml2code.java.annotation.jackson;

import com.xml2code.core.definition.FieldDefinition;
import com.xml2code.core.types.FieldType;
import com.xml2code.java.annotation.FieldAnnotation;

public class JsonDateDeserialize extends JacksonAnnotation implements
		FieldAnnotation {

	private FieldDefinition fieldDefinition;

	public JsonDateDeserialize(FieldDefinition fieldDefinition) {
		
		this.fieldDefinition = fieldDefinition;
		
	}
	
	public FieldDefinition getFieldDefinition() {
		
		return this.fieldDefinition;
		
	}

	@Override
	public String getCode() {

		if (this.fieldDefinition.getFieldType() == FieldType.date) {
			
			return "@JsonSerialize(using = DateJsonDeserializer.class)";

			
		} else if (this.fieldDefinition.getFieldType() == FieldType.datetime) {
			
			return "@JsonSerialize(using = DateTimeJsonDeserializer.class)";

			
		} else {
			
			return "";
			
		}
		
	}

}
