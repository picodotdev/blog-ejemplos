package io.github.picodotdev.bitix.jms;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * Ejemplo de como conectarse a un servidor JNDI de forma remota.
 */
public class Jndi {

	// Crear usuario con ./add-user.sh y dependencias
	public static void main(String[] args) throws Exception {
		// Usuario y password para conectarse al servidor JNDI
		String usuario = "guest";
		String contrasena = "guest";

		// Propiedades para crear el contexto: clase factoría, url del servidor JNDI y credenciales
		Properties env = new Properties();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
		env.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
		env.put(Context.SECURITY_PRINCIPAL, usuario);
		env.put(Context.SECURITY_CREDENTIALS, contrasena);

		// El objeto InitialContext permite obtener la referencias de los objetos registrado en el
		// ábol JNDI
		InitialContext ic = new InitialContext(env);

		// Obtener un objeto del registro que previanmente el administrador JNDI ha registrado
		Object connectionFactory = ic.lookup("jms/RemoteConnectionFactory");

		// Terminar liberando los recursos
		ic.close();
	}
}