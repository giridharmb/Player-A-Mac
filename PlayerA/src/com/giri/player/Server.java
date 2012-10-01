package com.giri.player;

//file: Server.java
import java.net.*;
import java.io.*;

public class Server {
	

	public void startServer(String args)
	{
	
		ServerSocket ss = null;
		try
		{
			System.out.println("# Creating new socket ...");
			ss = new ServerSocket(Integer.parseInt(args));
		}
		catch (NumberFormatException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (true)
		{
			try
			{
				System.out.println("# Starting server side connection ...");
				new ServerConnection(ss.accept()).start();
			}
			catch (SocketException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/*public static void main(String argv[]) throws IOException {
		ServerSocket ss = new ServerSocket(Integer.parseInt(argv[0]));
		while (true)
			new ServerConnection(ss.accept()).start();
	}*/
	
} // end of class Server

