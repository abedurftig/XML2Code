package com.xml2code.core.loader;

import com.xml2code.core.exception.ProjectLoadFailedException;
import org.junit.Assert;
import org.junit.Test;
import util.TestResourceUtil;

import java.io.File;
import java.net.URISyntaxException;

public class ProjectLoaderTest {

	@Test
	public void testProjectLoadNoDefinition() throws URISyntaxException {

		String projectFolder = "/project-definitions/project-no-project-def";
		File file = new File(TestResourceUtil.getResourceURI(projectFolder));

		try {

			ProjectLoader.loadProject(file.getAbsolutePath());
			Assert.fail("Expected exception!");

		} catch (ProjectLoadFailedException e) {

			Assert.assertEquals("Expected different message:", ProjectLoadFailedException.NO_PROJECT, e.getMessage());

		}

	}

	@Test
	public void testProjectLoadNoClassDefinitionDirectory() throws URISyntaxException {

		String projectFolder = "/project-definitions/project-no-class-dir";
		File file = new File(TestResourceUtil.getResourceURI(projectFolder));

		try {

			ProjectLoader.loadProject(file.getAbsolutePath());
			Assert.fail("Expected exception!");

		} catch (ProjectLoadFailedException e) {

			Assert.assertEquals("Expected different message:", ProjectLoadFailedException.NO_CLASS_DEF_DIRECTORY, e.getMessage());

		}

	}

	@Test
	public void testProjectLoadNoClassDefinitions() throws URISyntaxException {

		String projectFolder = "/project-definitions/project-no-class-def";
		File file = new File(TestResourceUtil.getResourceURI(projectFolder));

		try {

			ProjectLoader.loadProject(file.getAbsolutePath());
			Assert.fail("Expected exception!");

		} catch (ProjectLoadFailedException e) {

			Assert.assertEquals("Expected different message:", ProjectLoadFailedException.NO_CLASS_DEF, e.getMessage());

		}

	}
}
