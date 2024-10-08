package code;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client {
	private static Socket socket;
	private static DataInputStream in;
	private static DataOutputStream out;
	
	private static void receiveFile(String filename, long length) throws Exception {
		File file = new File(filename);
		//if (!file.createNewFile()) {
		int x = 1;
		String[] filenameArr;
		while (!file.createNewFile()) {
			filenameArr = filename.split(".");
			filenameArr[0] = filenameArr[0] + x;
			file = new File(String.join(".", filenameArr));
			x++;
		}
		//}
		int b;
		FileOutputStream fos = new FileOutputStream(file, true);
		for (long i=0; i<length; i++) {
			b = in.read();
			fos.write(b);
			System.out.print(b);
		}
		System.out.print("\n");
		if (!in.readUTF().equals("Server: transfer complete")) {
			throw new Exception("something went wrong");
		} else System.out.println("Server: transfer complete");
	}

	public static void main(String[] args) throws IOException {
		try {
			socket = new Socket("127.0.0.1", 5054);//replace with server IP
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
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
					receiveFile(content[2], Long.parseLong(content[4]));
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
