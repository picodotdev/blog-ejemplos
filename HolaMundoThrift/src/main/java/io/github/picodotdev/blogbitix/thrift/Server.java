package io.github.picodotdev.blogbitix.thrift;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

public class Server {

	public static void main(String[] args) {
		try {
			final Service.Processor<Service.Iface> processor = new Service.Processor<Service.Iface>(new Server.ServiceImpl());

			Runnable simple = new Runnable() {
				public void run() {
					simple(processor);
				}
			};

			new Thread(simple).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void simple(Service.Processor<Service.Iface> processor) {
		try {
			TServerTransport serverTransport = new TServerSocket(9090);
			TServer server = new TSimpleServer(new Args(serverTransport).processor(processor));

			// Use this for a multithreaded server
			// TServer server = new TThreadPoolServer(new
			// TThreadPoolServer.Args(serverTransport).processor(processor));

			System.out.println("Starting the service server...");
			server.serve();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static class ServiceImpl implements Service.Iface {

		@Override
		public String ping() throws TException {
			System.out.println("Me han llamado ¡que ilusión! ^^");
			
			return "¡Hola mundo!";
		}

		@Override
		public int add(int op1, int op2) throws TException {
			return op1 + op2;
		}

		@Override
		public String date() throws TException {
			return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss Z").format(new Date());
		}
	}
}