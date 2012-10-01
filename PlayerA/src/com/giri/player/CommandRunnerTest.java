package com.giri.player;
import java.awt.List;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;
import org.apache.commons.io.*;
import javax.swing.SwingWorker;

public class CommandRunnerTest implements Runnable {
		
	private String[] cmdToRun = null;
	public static int retCode = -1;

	public int getRetCode() {
		return retCode;
	}

	public void setRetCode(int retCode) {
		this.retCode = retCode;
	}
	
	public String[] getCmdToRun() {
		return cmdToRun;
	}

	public void setCmdToRun(String[] cmdToRun) {
		this.cmdToRun = cmdToRun;
	}

	public CommandRunnerTest(String[] cmd) {
		cmdToRun = cmd;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {

			String[] killCmd = {"killall" ,"-9", "PlayFile"};
			Process pKill = Runtime.getRuntime().exec(killCmd);
			pKill.waitFor();
			
			MainProcess.p = Runtime.getRuntime().exec(cmdToRun);
			
			retCode = MainProcess.p.waitFor();
			System.out.println("** Exit Code: " + retCode);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
