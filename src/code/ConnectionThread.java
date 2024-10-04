package code;

import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ConnectionThread extends Thread{
	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	private String h;
	
	public ConnectionThread (Socket socket) throws IOException {
		this.socket = socket;
		this.in = new DataInputStream(this.socket.getInputStream());
		this.out = new DataOutputStream(this.socket.getOutputStream());
		this.h = "Server: ";//message header
	}
	
	public void sendFile (String path) throws IOException, FileNotFoundException {
    	File file = new File(path);
    	String[] pathArray = path.split("\\\\");
    	String filename = pathArray[pathArray.length - 1];
    	out.writeUTF(this.h+"sending " + filename + " size: " + file.length());
    	out.flush();
    	FileInputStream fis = new FileInputStream(file);
    	int b;
    	while ((b = fis.read()) != -1) {
    		out.write(b);
    		out.flush();
    	}
    	out.writeUTF(this.h+"transfer complete");
    	out.flush();
    	fis.close();
	}
	
	public void run () {
		try {
			String message;
			String[] command;
			while (true) {
				message = in.readUTF();
				if (message.equals("exit")) {
					out.writeUTF(this.h+"bye");
					break;
				}
				command = message.split(" ");
				switch (command[0]) {
				case "download":
					System.out.println("sending " + command[1]);
					sendFile(command[1]);
					System.out.println(command[1] + " sent");
					break;
				case "upload":
					// TODO: receive file from client
					break;
				case "delete":
					// TODO: delete specified file in server storage
					break;
				default:
					out.writeUTF(this.h+"command not recognised");
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}
}
