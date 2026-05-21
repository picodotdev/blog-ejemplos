package io.github.picodotdev.bitix.jms;

import java.util.Properties;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * Ejemplo que muestra como como enviar y recibir mensajes JMS de un Queue de forma remota.
 */
public class Queue {

	/**
	 * Antes de ejecutar este ejemplo, usando WildFly se ha de crear un usuario guest y clave guest con el 
	 * script WILDFLY_HOME/bin/add-user.sh.
	 */
	public static void main(String[] args) throws Exception {
		// Usuario y password para conectarse al servidor JNDI y al Queue
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
		// - QueueConnectionFactory
		// - QueueConection
		// - Queue
		// - QueueSession
		// - QueueSubscriber
		// - QueuePublisher
		QueueConnectionFactory connectionFactory = (QueueConnectionFactory) ic.lookup("jms/RemoteConnectionFactory");
		QueueConnection connection = connectionFactory.createQueueConnection(usuario, contrasena);
		
		// Obtener el Queue en el cual se publicarán y del cual se recibirán los mensajes
		javax.jms.Queue queue = (javax.jms.Queue) ic.lookup("jms/queue/test");

		// Preparar el publicador y subscriptor al Queue
		Receiver receiver1 = new Receiver(connection, queue);
		Receiver receiver2 = new Receiver(connection, queue);
		Sender sender = new Sender(connection, queue);
		
		// Inicializar la recepción y envío de los mensajes
		connection.start();

		// Empezar a enviar mensajes en el Queue (y a recibirlos)
		Thread thread = new Thread(sender);		
		thread.start();
		// Esperar a que el enviador termine de enviar mensajes
		thread.join();

		// Parar el envío y recepción de mensajes
		connection.stop();
		
		// Terminar liberando los recursos
		receiver1.close();
		receiver2.close();
		sender.close();		
		connection.close();
		ic.close();
	}
	
	private static class Receiver implements MessageListener {
		
		private QueueSession session;
		private QueueReceiver receiver;
		
		public Receiver(QueueConnection connection, javax.jms.Queue queue) throws Exception {
			this.session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			this.receiver = this.session.createReceiver(queue);
			this.receiver.setMessageListener(this);
		}
		
		public void close() throws Exception  {
			receiver.close();
			session.close();
		}
		
		@Override
		public void onMessage(Message message) {
			try {
				TextMessage text = (TextMessage) message;
				System.out.printf("Receptor (%s): Un publicador dice: «%s»\n", this, text.getText());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private static class Sender implements Runnable {
		
		private QueueSession session;
		private QueueSender sender;
		
		public Sender(QueueConnection connection, javax.jms.Queue queue) throws Exception {
			this.session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			this.sender = this.session.createSender(queue);
		}
		
		public void close() throws Exception  {
			sender.close();
			session.close();
		}
		
		@Override
		public void run() {
			try {
				for (int i = 0; i < 10; ++i) {
					Message mensaje = session.createTextMessage(String.format("¡Hola mundo! (%d)", i));
					sender.send(mensaje);
					Thread.sleep(1000);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}