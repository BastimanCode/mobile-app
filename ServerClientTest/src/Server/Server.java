package Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

	public static void main(String[] args) {
		Server server = new Server(8001);
		server.startListening();
	}

	private int port;

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
						
						Scanner s = new Scanner(
								new BufferedReader(new InputStreamReader(remoteClientSocket.getInputStream())));
						if (s.hasNextLine()) {
							System.out.println(s.nextLine());
						}
	
						s.close();
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
