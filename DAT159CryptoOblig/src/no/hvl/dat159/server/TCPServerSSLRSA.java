package no.hvl.dat159.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SignatureException;

import javax.crypto.NoSuchPaddingException;
import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocketFactory;

import no.hvl.dat159.config.ServerConfig;
import no.hvl.dat159.crypto.Certificates;
import no.hvl.dat159.crypto.DigitalSignature;


public class TCPServerSSLRSA {
	
	private ServerSocket ssocket = null;
	private int port;
	
	public TCPServerSSLRSA(int port) {
		this.port = port;
		createSSLServerSocket();
	}
	
	private void createSSLServerSocket() {
		
		try {
			ServerSocketFactory ssf = SSLServerSocketFactory.getDefault();
			ssocket = ssf.createServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void socketlistener() throws NoSuchAlgorithmException, NoSuchPaddingException {
		
		try {
			
			System.out.println("[LISTENING:]");
			Socket socket = ssocket.accept();
			
			BufferedReader inmsg = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			DataOutputStream outmsg = new DataOutputStream(socket.getOutputStream());
			
			String clientmsg = inmsg.readLine();
			System.out.println("Message recieved from the Client: "+clientmsg);
					
			boolean valid = checkMessageForValidity(clientmsg, getPublicKey());
			
			String feedback = " ";
			if(valid)
				feedback = "message is valid";
			else
				feedback = "message is invalid - Signatures did not match";
			
			String response = "HTTP/1.1 200 OK \r\n\r\n"+ feedback;

			outmsg.write(response.getBytes());
			outmsg.flush();
			inmsg.close();
			outmsg.close();
			
			socket.close();
	
		}catch(IOException e) {
			
			e.printStackTrace();
		}
	}
	
	private boolean checkMessageForValidity(String messageandsignature, PublicKey publickey) {
		
		if(messageandsignature.startsWith("GET /")) {
			messageandsignature = messageandsignature.replace("GET /", "");
			messageandsignature = messageandsignature.replace("HTTP/1.1", "");
		}
		
		boolean isValid = false;
		
		String[] tokens = messageandsignature.trim().split("-");
		String message = tokens[0].replace("%20", " ");
		String signatureinhex = tokens[1];
		
		// implement me - verify signature and send the result
		
		return isValid;
		
	}
	
	
	private PublicKey getPublicKey() throws NoSuchAlgorithmException, NoSuchPaddingException {
		
		String certpath = "specify certificate path here";		// extract public key from the certificate file
		
		return Certificates.getPublicKey(certpath);
	}
	
	public static void main(String[] args) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException {
		// set the keystore dynamically using the system property
		
		// implement me
		
		TCPServerSSLRSA tcpserver = new TCPServerSSLRSA(ServerConfig.PORT);
		
		// start the server and let it run forever
		while(true) {
			tcpserver.socketlistener();
		}

	}

}
