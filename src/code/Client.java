package code;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client {
	private static Socket socket;
	private static DataInputStream in;
	private static DataOutputStream out;
	
	private static void receiveFile(String filename, long length) throws Exception {
		// TODO: write data to file
		//File file = new File(filename);
		//file.createNewFile();
		int b;
		for (long i=0; i<length; i++) {
			b = in.read();
			System.out.print(b);
		}
		if (!in.readUTF().equals("Server: transfer complete")) throw new Exception("something went wrong");
	}

	public static void main(String[] args) throws IOException {
		try {
			Socket socket = new Socket("127.0.0.1", 5054);//replace with server IP
			DataInputStream in = new DataInputStream(socket.getInputStream());
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			String message;
			String response;
			String[] content;
			do {
				System.out.print(">");
				message = input.readLine();
				out.writeUTF(message);
				out.flush();
				response = in.readUTF();
				System.out.println(response);
				content = response.split(" ");
				switch (content[1]) {
				case "sending":
					//receiveFile(content[2], Long.parseLong(content[4]));
					int b;
					for (long i=0; i<Long.parseLong(content[4]); i++) {
						b = in.read();
						System.out.print(b);
					}
					System.out.print("\n");
					if (!in.readUTF().equals("Server: transfer complete")) throw new Exception("something went wrong");
					break;
				case "receiving":
					// TODO: send file data to server
					break;
				}
			} while (!message.equals("exit"));
			socket.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
