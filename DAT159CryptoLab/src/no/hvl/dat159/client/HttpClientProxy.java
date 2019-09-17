package no.hvl.dat159.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;

import no.hvl.dat159.config.ProxyConfig;
import no.hvl.dat159.config.ServerConfig;

public class HttpClientProxy {
	
	private String server;
	private int port;
	
	public HttpClientProxy(String server, int port) {
		this.server = server;
		this.port = port;
	}
	
	public void doClient(String data) {
		
		char[] buffer = new char[1024];
		URL url;
		
		SocketAddress addr = new InetSocketAddress(ProxyConfig.SERVER, ProxyConfig.PORT);
		Proxy proxy = new Proxy(Proxy.Type.HTTP, addr);
		
		try {

			url = new URL("http://"+server+":"+port+"/"+data);
			
			HttpURLConnection con = (HttpURLConnection) url.openConnection(proxy);
			
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
		HttpClientProxy c = new HttpClientProxy(ServerConfig.SERVER, ServerConfig.PORT);
		c.doClient(data);

	}

}
