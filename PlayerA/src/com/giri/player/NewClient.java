package com.giri.player;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class NewClient {

    private  final String SERVER_IP = "127.0.0.1";
    private  final int SERVER_PORT = 1122;

    public void MainMethod(String args)
    {

        Socket socket = null;
        AllSongsObject aso = null;
        
        try
        {
            socket = new Socket(SERVER_IP, SERVER_PORT);
            System.out.println("# Client # : Connected to server!");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        	System.out.println("# Client # : Error connecting to server: " + ex.getMessage());
        }

        ObjectInputStream in = null;
        ObjectOutputStream out = null;

 
        try
        {
    
            out = new ObjectOutputStream(socket.getOutputStream());

            //read a string
            
            System.out.println("# Client # : Message entered is: " + args);

            //send it to server
            out.writeObject(new Message(args));
            out.flush();
            
            System.out.println("# Client # : Reading from input stream...");
      
            in = new ObjectInputStream(socket.getInputStream());
            
            aso = (AllSongsObject) in.readObject();
            
            System.out.println("# Client # : Server returned object of size: " + aso.get_ll().size());
            
            System.out.println("# Client # : Contents of the list: ");
            
            for(int i=0; i<aso.get_ll().size(); i++) {
            	System.out.println(aso.get_ll().get(i));
            }

        }
        catch (Exception ex)
        {
        	ex.printStackTrace();
            System.out.println("# Client # : Error: " + ex);
        }
        finally
        {
        	try 
        	{
            	out.close();
            	socket.close();
        	} catch(Exception e)
        	{
        		e.printStackTrace();
        	}
        }
    }
    
    public static void main(String[] args)
    {
    	new NewClient().MainMethod("giri");
    }
    
}