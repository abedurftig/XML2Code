package com.xml2code.core.definition.type;

import com.xml2code.core.definition.Field;
import com.xml2code.core.exception.UnsupportedFieldTypeException;

public enum FieldType {

	text,
	shorttext,
	bit,
	integer,
	decimal,
	date,
	datetime,
	id;

	public static String getType(Field field) throws UnsupportedFieldTypeException {

		FieldType type = field.getFieldType();

		switch (type) {
			case bit :
				return "boolean";
			case integer :
				return "int";
			case text :
			case shorttext :
				return "String";
			case decimal :
				return "BigDecimal";
			case date :
			case datetime :
				return "Date";
			case id :
				return "long";
			default :
				throw new UnsupportedFieldTypeException("The type '" + type + "' is not yet supported");
		}

	}

}
