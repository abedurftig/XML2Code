package util;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Provides useful methods related to resources used for testing.
 */
public class TestResourceUtil {

	/**
	 * Gets the URI for a resource.
	 * 
	 * @param name
	 * @return the URI to the resource with the specified name
	 * @throws java.net.URISyntaxException
	 */
	public static URI getResourceURI(String name) throws URISyntaxException {

		return TestPropertiesUtil.class.getResource(name).toURI();

	}
}
