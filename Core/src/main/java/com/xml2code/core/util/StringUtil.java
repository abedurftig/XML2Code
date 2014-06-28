package com.xml2code.core.util;

import org.apache.commons.lang.StringUtils;

public class StringUtil {

	public static String getPlural(String singular) {

		if (singular.endsWith("y")) {

			return singular.substring(0, singular.length() - 1) + "ies";

		} else if (singular.endsWith("s")) {

			return singular + "es";

		} else {

			return singular + "s";

		}

	}
	
	/**
	 * The specified String is split at every capital letter. 
	 * All parts are then transformed to upper case and joined with an underscore character.
	 * 
	 * @return the resulting String
	 */
	public static String joinWithUnderscore(String camelCase) {
		
		// split by upper case letter
		String[] words = camelCase.split("(?=\\p{Upper})");
		
		for (int i = 0; i < words.length; i++) {	
			words[i] = words[i].toUpperCase();
		}
		
		return StringUtils.join(words, "_");
		
	}

}
