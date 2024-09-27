package code;

import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class ConnectionThread extends Thread{
	private Socket socket;
	
	public ConnectionThread (Socket socket) {
		this.socket = socket;
	}
	
	public void run () {
		try {
			DataInputStream in = new DataInputStream(this.socket.getInputStream());
			DataOutputStream out = new DataOutputStream(this.socket.getOutputStream());
			String message;
			String h = "Server: ";
			while (true) {
				message = in.readUTF();
				if (message.equals("exit")) {
					out.writeUTF(h+"bye");
					break;
				}
				out.writeUTF(h+"command not recognised");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
