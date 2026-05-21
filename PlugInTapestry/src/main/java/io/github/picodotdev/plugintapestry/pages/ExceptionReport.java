package io.github.picodotdev.plugintapestry.pages;

import java.util.List;
import java.util.regex.Pattern;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.services.ExceptionReporter;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Session;

/**
 * @tapestrydoc
 */
public class ExceptionReport implements ExceptionReporter {

	private static final String PATH_SEPARATOR_PROPERTY = "path.separator";

	// Match anything ending in .(something?)path.
	private static final Pattern PATH_RECOGNIZER = Pattern.compile("\\..*path$");

	private String pathSeparator = System.getProperty(PATH_SEPARATOR_PROPERTY);

	@Property
	private String attributeName;

	@Inject
	@Property
	private Request request;

	@Inject
	@Symbol(SymbolConstants.PRODUCTION_MODE)
	@Property(write = false)
	private boolean productionMode;

	@Inject
	@Symbol(SymbolConstants.TAPESTRY_VERSION)
	@Property(write = false)
	private String tapestryVersion;

	@Inject
	@Symbol(SymbolConstants.APPLICATION_VERSION)
	@Property(write = false)
	private String applicationVersion;

	@Property(write = false)
	private Throwable rootException;

	@Property
	private String propertyName;

	@Override
	public void reportException(Throwable exception) {
		rootException = exception;
	}

	public boolean getHasSession() {
		return request.getSession(false) != null;
	}

	public Session getSession() {
		return request.getSession(false);
	}

	public Object getAttributeValue() {
		return getSession().getAttribute(attributeName);
	}

	/**
	 * Returns a <em>sorted</em> list of system property names.
	 */
	public List<String> getSystemProperties() {
		return InternalUtils.sortedKeys(System.getProperties());
	}

	public String getPropertyValue() {
		return System.getProperty(propertyName);
	}

	public boolean isComplexProperty() {
		return PATH_RECOGNIZER.matcher(propertyName).find() && getPropertyValue().contains(pathSeparator);
	}

	public String[] getComplexPropertyValue() {
		// Neither : nor ; is a regexp character.

		return getPropertyValue().split(pathSeparator);
	}
}