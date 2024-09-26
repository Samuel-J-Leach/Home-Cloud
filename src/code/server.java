package code;

import java.net.ServerSocket;
import java.net.Socket;

public class server {
	
	public static void main(String[] args) throws Exception {
		try {
			ServerSocket server = new ServerSocket(5054);
			while (true) {
				Socket connection = server.accept();
				connectionThread ct = new connectionThread(connection);
				ct.start();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
