package io.github.picodotdev.blogbitix.thrift;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class Client {

	public static void main(String[] args) {
	    try {
	      TTransport transport = new TSocket("localhost", 9090);
	      transport.open();

	      TProtocol protocol = new  TBinaryProtocol(transport);
	      Service.Client client = new Service.Client(protocol);

	      System.out.println(String.format("Ping: %s", client.ping()));
	      System.out.println(String.format("Add: %d", client.add(4, 7)));
	      System.out.println(String.format("Date: %s", client.date()));

	      transport.close();
	    } catch (TException e) {
	      e.printStackTrace();
	    } 
	}
}