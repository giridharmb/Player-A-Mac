package com.giri.player;

import java.io.Serializable;

/**
 *
 * @author Janith (http://cyberasylum.wordpress.com/)
 */
public class Message implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String message;

    public Message(String message) {
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}