package com.xml2code.core.generator;

public class ReplacementInstruction {

	private String pattern;
	private String replacement;
	private boolean replaceAll;

	public ReplacementInstruction(String pattern, String replacement, boolean replaceAll) {

		this.pattern = pattern;
		this.replacement = replacement;
		this.replaceAll = replaceAll;

	}

	public String getPattern() {
		return pattern;
	}

	public String getReplacement() {
		return replacement;
	}

	public boolean isReplaceAll() {
		return replaceAll;
	}
}
