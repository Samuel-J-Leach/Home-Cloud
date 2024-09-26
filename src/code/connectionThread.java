package code;

import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class connectionThread extends Thread{
	private Socket socket;
	
	public connectionThread (Socket socket) {
		this.socket = socket;
	}
	
	public void run () {
		try {
			DataInputStream in = new DataInputStream(this.socket.getInputStream());
			DataOutputStream out = new DataOutputStream(this.socket.getOutputStream());
			String message;
			while (true) {
				message = in.readUTF();
				if (message.equals("exit")) {
					out.writeUTF("bye");
					break;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
