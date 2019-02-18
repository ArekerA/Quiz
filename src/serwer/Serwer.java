package serwer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Serwer extends Thread {
	ServerSocket serverSocket = null;
	int port;
	public Serwer(int port) {
		super();
		this.port = port;
	}

	public void run() {
		try {
			serverSocket = new ServerSocket(port);
			while (!Thread.interrupted()) {
				Socket socket = serverSocket.accept();
				(new SerwerThread(socket)).start();
			}
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			if (serverSocket != null) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public void close()
	{
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
