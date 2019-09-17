package no.hvl.dat159.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import no.hvl.dat159.config.ServerConfig;

public class TCPClient {
	
	private String server;
	private int port;
	
	public TCPClient(String server, int port) {
		this.server = server;
		this.port = port;
	}
	
	public void clientProcess(String msg) {
		
		String outtxt = "";
		try {
			Socket csocket = new Socket(server, port);
			
			PrintWriter outmsg = new PrintWriter(csocket.getOutputStream(), true);
			BufferedReader inmsg = new BufferedReader(new InputStreamReader(csocket.getInputStream()));
			
			System.out.println("Message to TCPServer: "+msg);
			
			outmsg.println(msg);
			StringBuffer sb = new StringBuffer();
			while((outtxt = inmsg.readLine()) != null) {
				sb.append(outtxt+"\n");
			}
			
			System.out.println("Response from TCPServer: "+sb);	
			
			outmsg.close();
			inmsg.close();
			csocket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	
	public static void main(String[] args) {
		TCPClient c = new TCPClient(ServerConfig.SERVER, ServerConfig.PORT);
		c.clientProcess("This is TCPClient - sending text in the clear");
	}

}
