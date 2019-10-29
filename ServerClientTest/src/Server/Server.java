package Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

	public static void main(String[] args) {
		Server server = new Server(8000);
		server.startListening();
	}

	private int port;
	private String chat = "";

	public Server(int port) {
		this.port = port;
	}

	public void startListening() {

		new Thread(new Runnable() {

			@Override
			public void run() {

				while(true) {
					try {
						ServerSocket serverSocket = new ServerSocket(port);
						Socket remoteClientSocket = serverSocket.accept();
						
						Scanner s = new Scanner(new BufferedReader(new InputStreamReader(remoteClientSocket.getInputStream())));
						if (s.hasNextLine()) {							
							System.out.println(s.nextLine());							
						}
						
						PrintWriter pw = new PrintWriter(new OutputStreamWriter(remoteClientSocket.getOutputStream()));
						pw.print("hi");
						pw.flush();
						
						s.close();
						pw.close();						
						remoteClientSocket.close();
						serverSocket.close();
	
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}
