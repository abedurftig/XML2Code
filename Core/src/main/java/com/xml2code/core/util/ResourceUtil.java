package com.xml2code.core.util;

import java.io.InputStream;

public class ResourceUtil {

	public static InputStream getInputStream(String relativePath) {

		return ResourceUtil.class.getResourceAsStream(relativePath);

	}

}
