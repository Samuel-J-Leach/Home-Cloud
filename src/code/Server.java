package code;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	public static void main(String[] args) throws Exception {
		try {
			ServerSocket server = new ServerSocket(5054);
			while (true) {
				Socket connection = server.accept();
				ConnectionThread ct = new ConnectionThread(connection);
				ct.start();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
