package com.xml2code.java.generator.impl;

import java.util.ArrayList;
import java.util.List;

import com.xml2code.core.definition.ClassDefinition;
import com.xml2code.core.definition.ProjectDefinition;
import com.xml2code.core.generator.FileGenerator;
import com.xml2code.core.generator.ReplacementInstruction;
import com.xml2code.core.util.ResourceUtil;
import com.xml2code.core.util.StringConstants;
import com.xml2code.java.creator.ProjectCreator;
import com.xml2code.java.exception.JavaProjectCreationFailedException;
import com.xml2code.java.generator.ClassGenerator;
import com.xml2code.java.generator.IResourceFileGenerator;
import com.xml2code.java.generator.pattern.Pattern;
import com.xml2code.java.util.TemplateUtil;

public class ResourceFileGenerator extends FileGenerator implements IResourceFileGenerator {

	public void generateResourceFiles(ProjectDefinition projectDefinition, String targetDir) 
			throws JavaProjectCreationFailedException {

		try {
			
			generateMavenPomXML(projectDefinition);
			generateWebXML(projectDefinition, targetDir);
			generateHibernateCfgXML(projectDefinition, targetDir);
			
		} catch (Exception e) {
			
			throw new JavaProjectCreationFailedException(e);
			
		}
		
	}

	protected void generateMavenPomXML(ProjectDefinition projectDefinition) 
			throws Exception {
		
		String template = TemplateUtil.getXMLResourcePomXML();
		String contentFilePath = ProjectCreator.getJavaProjectPath(projectDefinition) + "/pom.xml";
		String dependencies = "";
		
		List<ReplacementInstruction> replacementInstructions = new ArrayList<ReplacementInstruction>();
		replacementInstructions.add(new ReplacementInstruction(Pattern.XML_GROUP_ID, 
				ClassGenerator.getRootPackage(projectDefinition), false));
		replacementInstructions.add(new ReplacementInstruction(Pattern.XML_ARTIFACT_ID, 
				projectDefinition.getProjectName(), false));
		replacementInstructions.add(new ReplacementInstruction(Pattern.XML_NAME, 
				projectDefinition.getProjectName(), false));
		replacementInstructions.add(new ReplacementInstruction(Pattern.XML_FINAL_NAME, 
				projectDefinition.getProjectName(), false));
		replacementInstructions.add(new ReplacementInstruction(Pattern.XML_DEPENDENCIES, 
				dependencies + StringConstants.NEW_LINE, false));
		
		writeOutFile(template, replacementInstructions, contentFilePath, ResourceUtil.FILE_TYPE_XML);
		
	}
	
	protected void generateWebXML(ProjectDefinition projectDefinition, String targetDir) 
			throws Exception {
		
	
		
	}
	
	protected void generateHibernateCfgXML(ProjectDefinition projectDefinition, String targetDir) 
			throws Exception {
		
		StringBuffer mappings = new StringBuffer();
		String domainPackage = ClassGenerator.getRootPackage(projectDefinition) + ".domain";
		String template = TemplateUtil.getXMLResourceHibernateCfgXML();
		String contentFilePath = targetDir + "/hibernate.cfg.xml";
		
		for (ClassDefinition classDef : projectDefinition.getClassDefinitions()) {
			
			String classMapping = domainPackage + "." + classDef.getClassName();
			mappings.append("\t\t<mapping class=\""+ classMapping +"\"/>" + StringConstants.NEW_LINE);
			
		}
		
		mappings.append("\t\t<mapping class=\""+ 
						domainPackage + "." + ClassDefinition.DEFAULT_SUPERCLASS + "\"/>" + 
						StringConstants.NEW_LINE);
		
		List<ReplacementInstruction> replacementInstructions = new ArrayList<ReplacementInstruction>();
		replacementInstructions.add(new ReplacementInstruction(Pattern.XML_MAPPINGS, mappings.toString(), false));
		replacementInstructions.add(new ReplacementInstruction(Pattern.XML_URL, projectDefinition.getProjectName(), false));
		
		writeOutFile(template, replacementInstructions, contentFilePath, ResourceUtil.FILE_TYPE_XML);
		
	}
	
}
