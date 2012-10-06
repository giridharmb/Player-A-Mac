package com.giri.player;

//file: Client.java
import java.net.*;
import java.util.LinkedList;
import java.io.*;

public class Client {
	/*
	public static void main(String argv[])
	{
		try
		{
			Socket server = new Socket("127.0.0.1", Integer.parseInt("1234"));
		
			// new Socket( argv[0], Integer.parseInt(argv[1]) );
			
			ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
			
			ObjectInputStream in = new ObjectInputStream(server.getInputStream());

			System.out.println("@ Printing date...");
			out.writeObject(new DateRequest());
			out.flush();
			System.out.println(in.readObject());

			System.out.println("@ Performing calculation...");
			out.writeObject(new MyCalculation(2));		
			out.flush();
			System.out.println(in.readObject());
			
			System.out.println("@ Parsing through the song list...");
			out.writeObject(new AllSongsObjectRequest(5));
			out.flush();
	
			//System.out.println(in.readObject());
			Object obj = in.readObject();
			LinkedList<String> ll = (LinkedList<String>) obj;
			System.out.println("@ Client: Size of linked list read: " + ll.size());
				
			
			System.out.println("@ Items in the list retrived from the server:");
			for(int i=0;i<ll.size(); i++) {
				System.out.println("ll["+i+"] => " + ll.get(i));
			}

			System.out.println("@ Requesting to play a song of specified INDEX value...");
			out.writeObject(new MySong(5));
			out.flush();
			
			server.close();
			
		}
		catch (IOException e)
		{
			System.out.println("I/O error " + e); // I/O error
		}
		catch (ClassNotFoundException e2)
		{
			System.out.println(e2); // unknown type of response object
		}
	}
	*/
}