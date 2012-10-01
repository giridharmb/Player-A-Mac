package com.giri.player;

import java.util.Random;

public class ProcessMonitor implements Runnable {

	public void run() {
		System.out.println("$$ Starting process monitor...");
		// TODO Auto-generated method stub
			
		while(true) {
			System.out.println(">> ProcessMonitor >> CommandRunnerTest.retCode = " + CommandRunnerTest.retCode);
			/*
			if(CommandRunnerTest.retCode == -1) {
				break;
			}
			*/
			if(CommandRunnerTest.retCode == 0 )
			{
				System.out.println(">> ProcessMonitor >>retCode = 0 !");
				
				if(MainWindow.keepPlaying)
				{
					System.out.println(">> ProcessMonitor >> keepPlaying = True");
					System.out.println("$$ Exit Code == 0 ! Doing a replay() ! ...");
					
					// TODO Auto-generated method stub
					if(MainWindow.allSongsLinkedList.size() == 0) {
						return;
					}
					
					Random rand = new Random();
					int totalSizeOfList = MainWindow.allSongsLinkedList.size();
					int randomIndex = rand.nextInt(totalSizeOfList);
					System.out.println("Random index => " + randomIndex);
								
					
					MainWindow.PLAY(randomIndex);
					CommandRunnerTest.retCode = -1;
				}
			} 
			
			try {
				Thread.currentThread().sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
