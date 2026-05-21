package io.github.picodotdev.plugintapestry.misc;

import java.io.File;
import java.net.URL;
import java.net.URLConnection;

import org.apache.tapestry5.ioc.services.ClasspathURLConverter;
import org.jboss.vfs.VFSUtils;
import org.jboss.vfs.VirtualFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WildFlyClasspathURLConverter implements ClasspathURLConverter {

	private static final Logger logger = LoggerFactory.getLogger(WildFlyClasspathURLConverter.class);

	@Override
	public URL convert(final URL url) {
		if (url != null && url.getProtocol().startsWith("vfs")) {
			try {
				final URL realURL;
				final String urlString = url.toString();
				// If the virtual URL involves a JAR file,
				// we have to figure out its physical URL ourselves because
				// in JBoss 7.0.2 the JAR files exploded into the VFS are empty
				// (see https://issues.jboss.org/browse/JBAS-8786).
				// Our workaround is that they are available, unexploded,
				// within the otherwise exploded WAR file.
				if (urlString.contains(".jar")) {
					// An example URL:
					// "vfs:/devel/jboss-as-7.1.1.Final/standalone/deployments/myapp.ear/myapp.war/WEB-INF/\
					// lib/tapestry-core-5.3.2.jar/org/apache/tapestry5/corelib/components/"
					// Break the URL into its WAR part, the JAR part,
					// and the Java package part.
					final int warPartEnd = urlString.indexOf(".war") + 4;
					final String warPart = urlString.substring(0, warPartEnd);
					final int jarPartEnd = urlString.indexOf(".jar") + 4;
					final String jarPart = urlString.substring(warPartEnd, jarPartEnd);
					final String packagePart = urlString.substring(jarPartEnd);
					// Ask the VFS where the exploded WAR is.
					final URL warURL = new URL(warPart);
					final URLConnection warConnection = warURL.openConnection();
					final VirtualFile jBossVirtualWarDir = (VirtualFile) warConnection.getContent();
					final File physicalWarDir = jBossVirtualWarDir.getPhysicalFile();
					final String physicalWarDirStr = physicalWarDir.toURI().toString();
					// Return a "jar:" URL constructed from the parts
					// eg.
					// "jar:file:/devel/jboss-as-7.1.1.Final/standalone/tmp/vfs/deployment40a6ed1db5eabeab/\
					// myapp.war-43e2c3dfa858f4d2/WEB-INF/lib/tapestry-core-5.3.2.jar!/org/apache/tapestry5/corelib/components/".
					final String actualJarPath = "jar:" + physicalWarDirStr + jarPart + "!" + packagePart;
					return new URL(actualJarPath);
				} else {
					// Otherwise, ask the VFS what the physical URL is...
					final URLConnection connection = url.openConnection();
					final VirtualFile virtualFile = (VirtualFile) connection.getContent();
					realURL = VFSUtils.getPhysicalURL(virtualFile);
				}
				return realURL;
			} catch (final Exception e) {
				logger.error("Unable to convert URL", e);
			}
		}
		return url;
	}
}
