package io.github.picodotdev.bitix.jms;

import java.util.Properties;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * Ejemplo que muestra como como enviar y recibir mensajes JMS de un Topic de forma remota.
 */
public class Topic {

	/**
	 * Antes de ejecutar este ejemplo, usando WildFly se ha de crear un usuario guest y clave guest con el 
	 * script WILDFLY_HOME/bin/add-user.sh.
	 */
	public static void main(String[] args) throws Exception {
		// Usuario y password para conectarse al servidor JNDI y al Topic
		String usuario = "guest";
		String contrasena = "guest";

		// Propiedades para crear el contexto: clase factoría, url del servidor JNDI y credenciales
		Properties env = new Properties();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
		env.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
		env.put(Context.SECURITY_PRINCIPAL, usuario);
		env.put(Context.SECURITY_CREDENTIALS, contrasena);

		// El objeto InitialContext permite obtener la referencias de los objetos registrado en el ábol JNDI
		InitialContext ic = new InitialContext(env);

		// Objetos a obtener para usar JMS: 
		// - TopicConnectionFactory
		// - TopicConection
		// - Topic
		// - TopicSession
		// - TopicSubscriber
		// - TopicPublisher
		TopicConnectionFactory connectionFactory = (TopicConnectionFactory) ic.lookup("jms/RemoteConnectionFactory");
		TopicConnection connection = connectionFactory.createTopicConnection(usuario, contrasena);
		
		// Obtener el Topic en el cual se publicarán y del cual se recibirán los mensajes
		javax.jms.Topic topic = (javax.jms.Topic) ic.lookup("jms/topic/test");

		// Preparar el publicador y subscriptor al Topic
		Subscriber subscriber1 = new Subscriber(connection, topic);
		Subscriber subscriber2 = new Subscriber(connection, topic);
		Publisher publisher = new Publisher(connection, topic);
		
		// Inicializar la recepción y envío de los mensajes
		connection.start();

		// Empezar a publicar mensajes en el Topic (y a recibirlos)
		Thread thread = new Thread(publisher);		
		thread.start();
		// Esperar a que el publicador termine de enviar mensajes
		thread.join();

		// Parar el envío y recepción de mensajes
		connection.stop();
		
		// Terminar liberando los recursos
		subscriber1.close();
		subscriber2.close();
		publisher.close();		
		connection.close();
		ic.close();
	}
	
	private static class Subscriber implements MessageListener {
		
		private TopicSession session;
		private TopicSubscriber subscriber;
		
		public Subscriber(TopicConnection connection, javax.jms.Topic topic) throws Exception {
			this.session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			this.subscriber = this.session.createSubscriber(topic, null, false);
			this.subscriber.setMessageListener(this);
		}
		
		public void close() throws Exception  {
			subscriber.close();
			session.close();
		}
		
		@Override
		public void onMessage(Message message) {
			try {
				TextMessage text = (TextMessage) message;
				System.out.printf("Suscriptor (%s): El publicador dice: «%s»\n", this, text.getText());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private static class Publisher implements Runnable {
		
		private TopicSession session;
		private TopicPublisher publisher;
		
		public Publisher(TopicConnection connection, javax.jms.Topic topic) throws Exception {
			this.session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			this.publisher = this.session.createPublisher(topic);
		}
		
		public void close() throws Exception  {
			publisher.close();
			session.close();
		}
		
		@Override
		public void run() {
			try {
				for (int i = 0; i < 10; ++i) {
					Message mensaje = session.createTextMessage(String.format("¡Hola mundo! (%d)", i));
					publisher.publish(mensaje);
					Thread.sleep(1000);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}