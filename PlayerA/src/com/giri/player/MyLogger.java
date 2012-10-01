package com.giri.player;


import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.XMLFormatter;

public class MyLogger {
	
	private LogManager lm = null;
    private Logger logger;
    private FileHandler fh = null;
    
    public MyLogger(String className) {
    	try {
			fh = new FileHandler("/tmp/player.log");
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	lm = LogManager.getLogManager();
    	logger = Logger.getLogger(className);
    	lm.addLogger(logger);
    	logger.setLevel(Level.INFO);
    	fh.setFormatter(new XMLFormatter());
    	logger.addHandler(fh);
    }        
    
    public void LOG(String msg) {
    	logger.log(Level.INFO, msg);
    }
    
    public void DONE() {
    	fh.close();
    }
}
