package code;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

public class Client {

	public static void main(String[] args) {
		try {
			Socket socket = new Socket("127.0.0.1", 5054);//replace with server IP
			DataInputStream in = new DataInputStream(socket.getInputStream());
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			String message;
			do {
				message = input.readLine();
				out.writeUTF(message);
				out.flush();
				System.out.println(in.readUTF());
			} while (!message.equals("exit"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
