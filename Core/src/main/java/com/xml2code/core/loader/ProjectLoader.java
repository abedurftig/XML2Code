package com.xml2code.core.loader;

import com.xml2code.core.definition.ProjectDef;
import com.xml2code.core.exception.ProjectLoadFailedException;

/** 
 * This class represents the entry point to the core project.
 * The <code>ProjectLoader</code> provides an interface which let
 * the caller retrieve a <code>ProjectDef</code> which will be based
 * on the project definition files in the specified folder.
 * 
 * @author dasnervtdoch
 *
 */
public class ProjectLoader {

	private ProjectDef projectDef = null;
	
	/**
	 * This method loads the project resources, validates, parses them and builds up 
	 * a <code>ProjectDef</code> instance which represents the specified project. The
	 * <code>ProjectDef</code> can then be retrieved from the <code>ProjectLoader</code>
	 * instance.
	 * 
	 * @see com.xml2code.core.loader.ProjectLoader#getProjectDef
	 * 
	 * @param projectFolder the folder which contains the <code>xml2code-project.xml</code> file and the classes folder
	 * @throws ProjectLoadFailedException
	 */
	public void loadProject(String projectFolder) throws ProjectLoadFailedException {
		
		// TODO: implement
		
	}
	
	/**
	 * @return the <code>ProjectDef</code> instance
	 */
	public ProjectDef getProjectDef() {
		
		return projectDef;
		
	}
	
}
