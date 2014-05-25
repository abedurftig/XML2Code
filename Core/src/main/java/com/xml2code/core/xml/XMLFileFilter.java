package com.xml2code.core.xml;

import java.io.File;
import java.io.FilenameFilter;


public class XMLFileFilter implements FilenameFilter {

	public boolean accept(File dir, String name) {
		
		return (name.endsWith(".xml"));
		
	}
	
}
