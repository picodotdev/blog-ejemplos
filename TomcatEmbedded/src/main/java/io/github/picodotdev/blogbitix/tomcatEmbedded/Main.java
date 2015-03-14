package io.github.picodotdev.blogbitix.tomcatEmbedded;

import org.apache.catalina.startup.Tomcat;

public class Main {

	public static void main(String[] args) throws Exception {
		Tomcat tomcat = new Tomcat();
		tomcat.setBaseDir("tomcat");
		tomcat.setPort(8080);
		
		// Para configurar el puerto seguro
		// http://www.copperykeenclaws.com/adding-an-https-connector-to-embedded-tomcat-7/
		// Connector httpsConnector = new Connector();
		// httpsConnector.setPort(443);
		// httpsConnector.setSecure(true);
		// httpsConnector.setScheme("https");
		// httpsConnector.setAttribute("keyAlias", keyAlias);
		// httpsConnector.setAttribute("keystorePass", password);
		// httpsConnector.setAttribute("keystoreFile", keystorePath);
		// httpsConnector.setAttribute("clientAuth", "false");
		// httpsConnector.setAttribute("sslProtocol", "TLS");
		// httpsConnector.setAttribute("SSLEnabled", true);
		//
		// Tomcat tomcat = new Tomcat();
		// Service service = tomcat.getService();
		// service.addConnector(httpsConnector);
		//
		// Connector defaultConnector = tomcat.getConnector();
		// defaultConnector.setRedirectPort(443);

		tomcat.addWebapp("/PlugInTapestry", "tomcat/webapps/PlugInTapestry.war");
		tomcat.start();
		
		// Puerto para enviar el comando SHUTDOWN
		// telnet localhost 8005
		// SHUTDOWN
		tomcat.getServer().setPort(8005);
		tomcat.getServer().await();
	}
}