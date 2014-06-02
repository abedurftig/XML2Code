package com.xml2code.core.generator;

import java.util.List;

import com.xml2code.core.util.StringConstants;

public class Replacer {

	private Replacer() {}

	public static String replace(String original, List<ReplacementInstruction> replacementInstructions) {

		for (ReplacementInstruction replacementInstruction : replacementInstructions) {

			if (replacementInstruction.isReplaceAll()) {

				original = original.replaceAll(replacementInstruction.getPattern(), replacementInstruction.getReplacement());

			} else {

				original = original.replaceFirst(replacementInstruction.getPattern(), replacementInstruction.getReplacement());

			}

		}
		
		original = original.replaceAll(StringConstants.TRIPPLE_NEW_LINE, StringConstants.DOUBLE_NEW_LINE);

		return original;

	}

}
