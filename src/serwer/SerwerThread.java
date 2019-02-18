package serwer;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import dane.Odpowiedz;
import dane.Test;


public class SerwerThread extends Thread {
	Socket mySocket;
	public SerwerThread(Socket socket){
		super(); // konstruktor klasy Thread
		mySocket = socket;
	}

	public void run()
	{
		try {
			if(mySocket.getLocalPort() == 753)
			{
				BufferedReader in = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
				String str = in.readLine();
				System.out.println(mySocket.getInetAddress() + " : " + str);
				String[] data = str.split(",");
				OutputStream outputStream = mySocket.getOutputStream();
		        ObjectOutputStream objOutputStream = new ObjectOutputStream(outputStream);
				switch (data[0]) {
				case "getTest":
					System.out.println("zaczynam getTest");
			        objOutputStream.writeObject(JDBC.pobierzTest(Integer.parseInt(data[1])));
		            objOutputStream.flush();
					System.out.println("kończę getTest");
					break;
				case "getTesty":
					System.out.println("zaczynam getTesty");
			        objOutputStream.writeObject(JDBC.pobierzTesty());
		            objOutputStream.flush();
					System.out.println("kończę getTesty");
					break;
	
				default:
					break;
				}
				System.out.println(mySocket.getInetAddress() + " : rozłączam");
			}
			else if(mySocket.getLocalPort() == 754)
			{
				System.out.println("zaczynam updateOdp");
				InputStream inputStream = mySocket.getInputStream();
				ObjectInputStream objInputStream = null;
				objInputStream = new ObjectInputStream(inputStream);
				String user = (String) objInputStream.readObject();
				int idUser = JDBC.pobierzUser(user);
	            ArrayList<Odpowiedz> p = (ArrayList<Odpowiedz>) objInputStream.readObject();
	            for(Odpowiedz o : p)
	            	JDBC.dodajLicznikOdpowiedzi(o.getId());
	            for(Odpowiedz o : p)
	            	JDBC.dodajUserOdp(idUser, o.getId());
				System.out.println("kończę updateOdp");
			}
			mySocket.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
