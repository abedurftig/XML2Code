package com.xml2code.application;

import com.xml2code.core.definition.ProjectDefinition;
import com.xml2code.core.exception.ProjectLoadFailedException;
import com.xml2code.core.loader.ProjectLoader;
import com.xml2code.java.creator.ProjectCreator;
import com.xml2code.java.exception.JavaProjectCreationFailedException;

public class Main {
	
	public static void main(String[] args) {
		
		String projectFolder = "/Users/Arne/Workspaces/Languageschool";
		
		try {
			
			ProjectDefinition projectDefinition = ProjectLoader.loadProject(projectFolder);
			ProjectCreator.createJavaProject(projectDefinition);
			
		} catch (ProjectLoadFailedException e) {
			e.printStackTrace();
		} catch (JavaProjectCreationFailedException e) {
			e.printStackTrace();
		}
		
	}
	
}
