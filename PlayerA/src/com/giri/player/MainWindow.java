package com.giri.player;


import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLDecoder;
import java.util.LinkedList;
import java.util.Random;

import java.lang.*;
import java.io.*;
import java.net.*;


import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import org.apache.commons.io.*;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.beans.VetoableChangeListener;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JCheckBox;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class MainWindow extends JFrame implements MainPlayer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Process p_volume = null;
	private JFileChooser fc = null;
	public static LinkedList<String> allSongsLinkedList = new LinkedList<String>();
	public static LinkedList<String> allSongsFileNamesOnly = new LinkedList<String>();
	private List list = null;
			
	//public static CommandRunnerTest CR = new CommandRunnerTest();
	
	private static NewServer server_thread = null;
	private static ProcessMonitor processMonitor = null;
	public static Thread thread_ProcessMonitor = new Thread("ProcessMonitor");
	
	public static boolean REPLAY_NOW = false;
	
	public static PlayerState CurrentPlayerState;
	private JTextField textField;
	private JScrollBar scrollBar = null;
	
	public static boolean keepPlaying =false;
	
	public static JCheckBox chckbxNewCheckBox = null;
		
	
	// System.getProperty("user.home")
				
	
	public static void PLAY(int index) {
		playIndex(index);
	}
	
	private void populateList() {
		allSongsLinkedList = getAllFiles(System.getProperty("user.home")+"/__media__","mp3");
	}
	
	// fileDir = /Users/giridhar/songs/english , extension = mp3
	private LinkedList<String> getAllFiles(String fileDir, String extension) {
		System.out.println("getAllFiles(), Input fileDir="+fileDir+", extension="+extension);
		LinkedList<String> myLinkedList = new LinkedList<String>();
		File dir = new File(fileDir);
		FileFilter fileFilter = new WildcardFileFilter("*." + extension);
		File[] files = dir.listFiles(fileFilter);
			
		for(File f : files) {
			String absPathOfFile = f.getAbsolutePath();
			myLinkedList.add(absPathOfFile);
			list.add(f.getName());
			allSongsFileNamesOnly.add(f.getName());
		}
		System.out.println("Size of myLinkedList returned: "+myLinkedList.size());
		return myLinkedList;
	}
	
	private void setVolumeTextAndVolume(int level) {
		_setvolume(level);
	}
	
	public synchronized static void setVolume(int level) {
		if(level>=0 && level<=100) {
			String volume = Integer.toString(level);
			String cmd[] = { "osascript", "/bin/getsetvolume.scpt" ,"SET", volume };
			try {
				Process volProc = Runtime.getRuntime().exec(cmd);
				volProc.waitFor();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private synchronized void playFile(File f) {
		
		String pathToFile = f.getAbsolutePath();
		String playCommand = "PlayFile " + pathToFile;
		String[] playCmd = { "PlayFile" , pathToFile };
		new Thread(new CommandRunnerTest(playCmd),"Command Runner").start();
		//CR.setCmdToRun(playCmd);
		//CR.EXECUTE();
		
	}
	
	public static String getVolume()  {
		String ret = null;
		String line = null;
		String cmd[] = { "osascript", "/bin/getsetvolume.scpt" ,"GET" };
		try {
			Process volProc = Runtime.getRuntime().exec(cmd);
			volProc.waitFor();
			BufferedReader in = new BufferedReader(new InputStreamReader(volProc.getInputStream()) );
		    while ((line = in.readLine()) != null)
		    {
		    	System.out.println(line);
		    	ret = line.toString();
		    	break;
		    }
		    
		    in.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret; 
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
        System.setProperty("Quaqua.tabLayoutPolicy","wrap");
        
        try { 
             UIManager.setLookAndFeel(ch.randelshofer.quaqua.QuaquaManager.getLookAndFeel());
        } catch (Exception e) {
        
            e.printStackTrace();
        }
        
        processMonitor = new ProcessMonitor();
        thread_ProcessMonitor = new Thread(processMonitor);
		thread_ProcessMonitor.start();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
					System.out.println("# Starting server thread...");
					//Thread server_thread = new ServerThread();
					server_thread = new NewServer("testServer");
					server_thread.start();
					System.out.println("# Server thread started ...");
															
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	
	}
	
	
	private void playRandomSong() {
		_playrandom();
	}
	
	private synchronized static void playIndex(int value) {
		
		System.out.println("@ Play message received. Requested index: " + value);
		
		if(value >= 0 && value < allSongsLinkedList.size() )
		{
			String fileToPlay = allSongsLinkedList.get(value);
			String playCommand = "PlayFile " + fileToPlay;
			String cmd[] = {"PlayFile",	fileToPlay};
					
			System.out.println("fileToPlay: " + fileToPlay);
			System.out.println("Play Command: " + playCommand);
			
			//CR.setCmdToRun(cmd);
			//CR.EXECUTE();
			new Thread(new CommandRunnerTest(cmd),"Command Runner").start();
			
			/*
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			
		}
	
	}
	
	/**
	 * Create the frame.
	 */
	public MainWindow() {
		
		//replayManager = new ReplayManager();
		//replayManager.start();
		
				
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 767, 572);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		CurrentPlayerState = PlayerState.STOPPED;
		
		list = new List();
		
		populateList();
		
		JButton btnPlay = new JButton("Play");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				_play();
			}
		});
		
		btnPlay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
			}
		});
		btnPlay.setBounds(31, 421, 117, 29);
		contentPane.add(btnPlay);
		
		JButton btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				_stop();
			}
		});
		btnStop.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
			
			}
		});
		btnStop.setBounds(289, 421, 117, 29);
		contentPane.add(btnStop);
		
		JLabel lblNewLabel = new JLabel("Browse Folder");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(6, 6, 253, 16);
		contentPane.add(lblNewLabel);
		
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fc.showOpenDialog(MainWindow.this);
				
		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fc.getSelectedFile();
		            //This is where a real application would open the file.
		            System.out.println("Selected folder: " + file.getAbsolutePath());
		            allSongsLinkedList = getAllFiles(file.getAbsolutePath(),"mp3");
		            		            
		        } else {
		            System.out.println("Operation cancelled by user ...!");
		        }
			}
		});
		btnBrowse.setBounds(31, 34, 707, 29);
		contentPane.add(btnBrowse);
		
		
		list.setBounds(31, 69, 709, 334);
		contentPane.add(list);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				_exit();
			}
		});
		btnExit.setBounds(418, 421, 117, 29);
		contentPane.add(btnExit);
		
		JLabel lblGiridharsMpPlayer = new JLabel("Giridhar's MP3 Player for Mac OSX (giridharmb@gmail.com)");
		lblGiridharsMpPlayer.setHorizontalAlignment(SwingConstants.CENTER);
		lblGiridharsMpPlayer.setBounds(346, 6, 415, 16);
		contentPane.add(lblGiridharsMpPlayer);
		
		JButton btnPlayRandom = new JButton("Play Random");
		btnPlayRandom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				playRandomSong();
			}
		});
		btnPlayRandom.setBounds(160, 421, 117, 29);
		contentPane.add(btnPlayRandom);
		
		JLabel lblVolume = new JLabel("Volume");
		lblVolume.setHorizontalAlignment(SwingConstants.CENTER);
		lblVolume.setBounds(31, 479, 61, 16);
		contentPane.add(lblVolume);
		
		scrollBar = new JScrollBar();
		scrollBar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				setVolumeTextAndVolume(scrollBar.getValue());
			}
		});
		scrollBar.setOrientation(JScrollBar.HORIZONTAL);
		scrollBar.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				setVolumeTextAndVolume(scrollBar.getValue());
			}
		});
		scrollBar.addVetoableChangeListener(new VetoableChangeListener() {
			public void vetoableChange(PropertyChangeEvent arg0) {
				
			}
		});
		scrollBar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				
			}
		});
		scrollBar.setBounds(31, 507, 709, 16);
		contentPane.add(scrollBar);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setBounds(682, 473, 61, 28);
		contentPane.add(textField);
		textField.setColumns(10);
		
		chckbxNewCheckBox = new JCheckBox("Keep Playing");
		chckbxNewCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(chckbxNewCheckBox.isSelected()) {
					keepPlaying = true;
				} else {
					keepPlaying = false;
				}
			}
		});
		chckbxNewCheckBox.setHorizontalAlignment(SwingConstants.CENTER);
		chckbxNewCheckBox.setBounds(601, 422, 117, 23);
		contentPane.add(chckbxNewCheckBox);
		
		scrollBar.setValue(Integer.parseInt(getVolume()));
		textField.setText(getVolume());
	}

	@Override
	public synchronized void _play() {
		
		System.out.println("_play() : Request to play the choosen song in the playlist...");
		
		// TODO Auto-generated method stub
		if(list.getItemCount() == 0) {
			return;
		}
		try
		{
					
			System.out.println("@ Play message received...");
			
			int selectedIndex = list.getSelectedIndex();
			System.out.println("Selected index: " + selectedIndex);
			
			String fileToPlay = allSongsLinkedList.get(selectedIndex);
			System.out.println("fileToPlay: " + fileToPlay);
			
			String playCommand = "PlayFile " + fileToPlay;
			System.out.println("Play Command: " + playCommand);
			
			String cmd[] = {"PlayFile",	fileToPlay};	
			
			new Thread(new CommandRunnerTest(cmd),"Command Runner").start();
			//CR.setCmdToRun(cmd);
			//CR.EXECUTE();
						
			
			CurrentPlayerState = PlayerState.PLAYING;
			
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public synchronized void _stop() {
		System.out.println("_stop() : Stop message received...");
		// TODO Auto-generated method stub
		if(list.getItemCount() == 0) {
			return;
		}
		
		String[] killCmd = {"killall" ,"-9", "PlayFile"};
		
		try 
		{
			Process pKill = Runtime.getRuntime().exec(killCmd);
			pKill.waitFor();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		server_thread.StopServer();
		if(CurrentPlayerState != PlayerState.STOPPED) {
			//CR.stopExecution();
			CurrentPlayerState = PlayerState.STOPPED;
			System.out.println("@ Success ! Stopped !");
		} else {
			System.out.println("@ Oops ! Already Stopped !");
		}
		
	}

	@Override
	public synchronized void _exit()  {
		System.out.println("_exit() : Exit message received...");
		// TODO Auto-generated method stub
		if(list.getItemCount() == 0) {
			return;
		}	
		
		//if(CR != null) {
			//CR.stopExecution();
		//}
		
		String[] killCmd = {"killall" ,"-9", "PlayFile"};
		
		try 
		{
			Process pKill = Runtime.getRuntime().exec(killCmd);
			pKill.waitFor();
		} catch(Exception e) {
			e.printStackTrace();
		}
		server_thread.StopServer();
		System.exit(0);
	}

	@Override
	public void _playrandom() {
		System.out.println("_playrandom() : Request to play a random song...");
		// TODO Auto-generated method stub
		if(list.getItemCount() == 0) {
			return;
		}
		Random rand = new Random();
		int totalSizeOfList = allSongsLinkedList.size();
		int randomIndex = rand.nextInt(totalSizeOfList);
		System.out.println("Random index => " + randomIndex);
		playIndex(randomIndex);
		list.select(randomIndex);
		
	}

	@Override
	public void _setvolume(int volume) {
		// TODO Auto-generated method stub
		setVolume(volume);
		textField.setText(Integer.toString(scrollBar.getValue()));
	}
}

