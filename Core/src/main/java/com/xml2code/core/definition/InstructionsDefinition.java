package com.xml2code.core.definition;

public class InstructionsDefinition {

	private boolean generateJava = true;
	
	private boolean generateRelationalMapping = true;
	
	private boolean generateApi = true;
	
	private boolean generateValidation = true;
	
	private boolean generateWeb = true;

	/**
	 * @return the generateJava
	 */
	public boolean isGenerateJava() {
		return generateJava;
	}

	/**
	 * @param generateJava the generateJava to set
	 */
	public void setGenerateJava(boolean generateJava) {
		this.generateJava = generateJava;
	}

	/**
	 * @return the generateRelationalMapping
	 */
	public boolean isGenerateRelationalMapping() {
		return generateRelationalMapping;
	}

	/**
	 * @param generateRelationalMapping the generateRelationalMapping to set
	 */
	public void setGenerateRelationalMapping(boolean generateRelationalMapping) {
		this.generateRelationalMapping = generateRelationalMapping;
	}

	/**
	 * @return the generateApi
	 */
	public boolean isGenerateApi() {
		return generateApi;
	}

	/**
	 * @param generateApi the generateApi to set
	 */
	public void setGenerateApi(boolean generateApi) {
		this.generateApi = generateApi;
	}

	/**
	 * @return the generateValidation
	 */
	public boolean isGenerateValidation() {
		return generateValidation;
	}

	/**
	 * @param generateValidation the generateValidation to set
	 */
	public void setGenerateValidation(boolean generateValidation) {
		this.generateValidation = generateValidation;
	}

	/**
	 * @return the generateWeb
	 */
	public boolean isGenerateWeb() {
		return generateWeb;
	}

	/**
	 * @param generateWeb the generateWeb to set
	 */
	public void setGenerateWeb(boolean generateWeb) {
		this.generateWeb = generateWeb;
	}
}
