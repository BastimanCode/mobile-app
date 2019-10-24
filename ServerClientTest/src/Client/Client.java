package Client;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {
	
	public static void main(String[] args) {
		Client client = new Client("192.168.0.80", 8002, "Kathi");
		client.sendMessage("Lass uns schlafen!");
	}
	
	private InetSocketAddress address;
	private String clientname;
	
	public Client(String hostname, int port, String clientname) {
		address = new InetSocketAddress(hostname, port);
		this.clientname = clientname;
	}
	
	public void sendMessage(String msg) {
		try {
			Socket socket = new Socket();
			socket.connect(address, 5000);
			
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			pw.print(clientname + ": " + msg);
			pw.flush();
			
			pw.close();
			socket.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
