package com.giri.player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

/**
 *
 * @author Janith (http://cyberasylum.wordpress.com/)
 */
public class NewServer extends Thread {
	
    private final int PORT = 8081;

    private String myString = null;
    
    private static ServerSocket serverSocket = null;
    private static Socket socket = null;
    
    private Message message = null;
    
    public NewServer(String mystr) {
    	myString = mystr;
    }
    
    public void StartServer() {
    	
    	String args = myString; 
    	
    	AllSongsObject aso = new AllSongsObject(MainWindow.allSongsFileNamesOnly);
    	
    	try
        {
            serverSocket = new ServerSocket();
            serverSocket.setReuseAddress(true);
            serverSocket.bind(new InetSocketAddress(PORT));
        }
        catch (Exception ex)
        {
        	ex.printStackTrace();
            System.out.println("# Server # : Error occured while creating the server socket");
            return;
        }
    	        
        while (true)
        {
            try
            {
            
                socket = serverSocket.accept();
                                          
                //Now we have established the a connection with the client
                
                System.out.println("# Server # : Connection created, client IP: " + socket.getInetAddress());
                
                ObjectInputStream in = null;
                ObjectOutputStream out = null;
       
                in = new ObjectInputStream(socket.getInputStream());
                
                 message = (Message) in.readObject();
                
                System.out.println("# Server # : Client said: " + message.getMessage());
                
                if(message.getMessage().equals(Constants.GETPLAYLIST))
                {
                	out = new ObjectOutputStream(socket.getOutputStream());
                    out.writeObject(aso);
                    out.flush();
               
                }
                else if(message.getMessage().contains(Constants.PLAYINDEX))
                {
                	String[] cmd = message.getMessage().split("@");
                	String indexStr = cmd[1];
                	int index = Integer.parseInt(indexStr);
                	MainWindow.PLAY(index);
                	
                	out = new ObjectOutputStream(socket.getOutputStream());
                	out.writeObject((Object)null);
                	out.flush();
                }
                else if(message.getMessage().contains(Constants.LOOP)) {
                	MainWindow.chckbxNewCheckBox.setSelected(true);
                } else if(message.getMessage().contains(Constants.DONTLOOP)) {
                	MainWindow.chckbxNewCheckBox.setSelected(false);
                }
            }
            
            catch (Exception ex)
            {
            	ex.printStackTrace();
                System.out.println("# Server # : Error: " + ex.getMessage());
                
                //try {
                //	socket.close();
                //	serverSocket.close();
                //} catch(Exception e) {
                //	System.out.println("Error in closing the sockets ... " + e.getMessage());
                //}
            }
            
            try {
				Thread.currentThread().sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } // while (true)
    }
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		StartServer();
	}
    
	public void StopServer() {
		try {
			if(!socket.isClosed()) {
				socket.close();
			}
			if(!serverSocket.isClosed()) {
				serverSocket.close();
			}
		} catch(Exception e) {
			System.out.println("Error in shutting down the server ... " + e.getMessage());
		}
	}
    
}