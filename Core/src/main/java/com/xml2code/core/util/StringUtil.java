package com.xml2code.core.util;

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

}
