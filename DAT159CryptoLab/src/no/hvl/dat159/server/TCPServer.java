package no.hvl.dat159.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import no.hvl.dat159.config.ServerConfig;


public class TCPServer {

	private ServerSocket ssocket;
	
	public TCPServer(int port) throws IOException {
		ssocket = new ServerSocket(port);
	}
	
	public void socketlistener() {
		
		try {
			
			System.out.println("TCP Server listening to incoming connections from clients >>");
			Socket socket = ssocket.accept();
			
			BufferedReader inmsg = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			DataOutputStream outmsg = new DataOutputStream(socket.getOutputStream());
			
			String clientmsg = inmsg.readLine();
			System.out.println("Message recieved from the Client: "+clientmsg);
			
			String response = "HTTP/1.1 200 OK \r\n\r\n"+ "Thanks TCPClient - I got your message";

			outmsg.write(response.getBytes());
			outmsg.flush();
			inmsg.close();
			outmsg.close();
			
			socket.close();
	
		}catch(IOException e) {
			//
		}
	}
	
	public static void main(String[] args) throws IOException {

		TCPServer tcpserver = new TCPServer(ServerConfig.PORT);
		
		// start the server and let it run forever
		while(true) {
			tcpserver.socketlistener();
		}

	}

}
