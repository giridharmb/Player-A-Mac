package com.giri.player;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class ServerConnection extends Thread {
	Socket client;

	ServerConnection(Socket client) throws SocketException {
		this.client = client;
	}

	public void run()
	{
		try
		{
			ObjectInputStream in = new ObjectInputStream( client.getInputStream());
			
			ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
			
			while (true)
			{
				out.writeObject(processRequest(in.readObject()));
				out.flush();
			}
			
		}
		catch (EOFException e3)
		{ // Normal EOF
			try
			{
				client.close();
			}
			catch (IOException e)
			{
				// nothing
			}
		}
		catch (IOException e)
		{
			System.out.println("I/O error " + e); // I/O error
		} 
		catch (ClassNotFoundException e2) 
		{
			System.out.println(e2); // unknown type of request object
		}
	}

	private Object processRequest(Object request)
	{
		if (request instanceof DateRequest)
		{
			return new java.util.Date();
		}
		else if (request instanceof WorkRequest)
		{
			return ((WorkRequest) request).execute();
		}
		else if (request instanceof AllSongsObjectRequest) 
		{
			return (Object) MainWindow.allSongsFileNamesOnly;
		}
		else if (request instanceof SongRequest)
		{
			return ((SongRequest) request).execute();
		}
		else
		{
			return null;
		}
	}
}