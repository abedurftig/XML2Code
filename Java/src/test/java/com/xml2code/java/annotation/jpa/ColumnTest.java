package com.xml2code.java.annotation.jpa;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.xml2code.core.definition.FieldDefinition;
import com.xml2code.core.types.FieldType;

/**
 * Unit tests for class: {@link com.xml2code.java.annotation.jpa.Column}.
 */
@RunWith(JUnit4.class)
public class ColumnTest {
	
	@Test
	public void testOneWordFieldName() {
		
		FieldDefinition fieldDef = new FieldDefinition("address", FieldType.bit, false);
		Column fieldAnnotation = new Column(fieldDef);
		
		String code = fieldAnnotation.getCode();
		
		Assert.assertTrue("Failure - should contain 'ADDRESS'", code.contains("ADDRESS"));
		
	}
	
	@Test
	public void testTwoWordFieldName() {
		
		FieldDefinition fieldDef = new FieldDefinition("firstName", FieldType.bit, false);
		Column fieldAnnotation = new Column(fieldDef);
		
		String code = fieldAnnotation.getCode();
		
		Assert.assertTrue("Failure - should contain 'FIRST_NAME'", code.contains("FIRST_NAME"));
		
	}
	
	@Test
	public void testMultipleWordFieldName() {
		
		FieldDefinition fieldDef = new FieldDefinition("contactAddressPreference", FieldType.bit, false);
		Column fieldAnnotation = new Column(fieldDef);
		
		String code = fieldAnnotation.getCode();
		
		Assert.assertTrue("Failure - should contain 'CONTACT_ADDRESS_PREFERENCE'", code.contains("CONTACT_ADDRESS_PREFERENCE"));
		
	}
	
	@Test
	public void testFieldRequiredTrue() {
		
		FieldDefinition fieldDef = new FieldDefinition("name", FieldType.bit, true);
		Column fieldAnnotation = new Column(fieldDef);
		
		String code = fieldAnnotation.getCode();
		
		Assert.assertTrue("Failure - should contain 'nullable = false'", code.contains("nullable = false"));
		
	}
	
	@Test
	public void testFieldRequiredFalse() {
		
		FieldDefinition fieldDef = new FieldDefinition("name", FieldType.bit, false);
		Column fieldAnnotation = new Column(fieldDef);
		
		String code = fieldAnnotation.getCode();
		
		Assert.assertTrue("Failure - should contain 'nullable = true'", code.contains("nullable = true"));
		
	}
	
	@Test
	public void testFieldRequiredTypeShortText() {
		
		FieldDefinition fieldDef = new FieldDefinition("name", FieldType.shorttext, false);
		Column fieldAnnotation = new Column(fieldDef);
		
		String code = fieldAnnotation.getCode();
		
		Assert.assertTrue("Failure - should contain 'length = 255'", code.contains("length = 255"));
		
	}
	
	@Test
	public void testFieldRequiredTypeText() {
		
		FieldDefinition fieldDef = new FieldDefinition("name", FieldType.text, false);
		Column fieldAnnotation = new Column(fieldDef);
		
		String code = fieldAnnotation.getCode();
		
		Assert.assertTrue("Failure - should contain 'length = 10000'", code.contains("length = 10000"));
		
	}
	
}
