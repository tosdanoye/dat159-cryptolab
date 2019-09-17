package no.hvl.dat159.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import no.hvl.dat159.config.ServerConfig;

public class HttpClient {
	
	private String server;
	private int port;
	
	public HttpClient(String server, int port) {
		this.server = server;
		this.port = port;
	}
	
	public void doClient(String data) {
		
		char[] buffer = new char[1024];
		URL url;
		
		try {

			url = new URL("http://"+server+":"+port+"/"+data);
			
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			
			br.read(buffer, 0, buffer.length);
			
			System.out.println(new String(buffer));
			
			con.disconnect();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	
	public static void main(String[] args) {
		String data = "?message=random stuff";
		HttpClient c = new HttpClient(ServerConfig.SERVER, ServerConfig.PORT);
		c.doClient(data);

	}

}
