package com.xml2code.core.generator;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.xml2code.core.util.ResourceUtil;

public abstract class FileGenerator {

	protected void writeOutFile(String template, List<ReplacementInstruction> replacementInstructions,
			String contentFilePath, String fileTyp) throws IOException {
		
		String content = Replacer.replace(template, replacementInstructions);
		File contentFile = new File(contentFilePath);
		
		ResourceUtil.writeContentToFile(contentFile, content, fileTyp);
		
	}
}
