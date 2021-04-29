import java.applet.Applet;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class yeltsin extends Applet implements ActionListener{

	private static final long serialVersionUID = 1L;

	String name;
	JPanel panel=new JPanel();
	JFrame frame=new JFrame("Yeltsin - Movie Editor");
	JButton createFile = new JButton("Create New Movie File");
	JButton editFile = new JButton("Edit Selected Movie File");
	JButton refreshList;
	MovieFileEditor fileEditor;
	@SuppressWarnings("rawtypes")
	JComboBox movieMenu;
	ImageIcon frameLogo = new ImageIcon("YeltsinLogo.png");
	CommandPrompt OpenVLC;
	YeltsinScheduler YTimer;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void init(){
		
		File movieDirectory = new File("Movies");
		File movieFiles[] = movieDirectory.listFiles();
		String movies[] = new String[movieFiles.length];
		for(int i = 0; i < movieFiles.length; i++){
			movies[i] = movieFiles[i].getName();
			movies[i] = movies[i].substring(0, movies[i].length()-4);
		}
		Arrays.sort(movies);
		
		JPanel buttonPanel = new JPanel(new GridLayout(1,2));
		buttonPanel.add(createFile);
		buttonPanel.add(editFile);
		movieMenu=new JComboBox(movies);
		movieMenu.insertItemAt("select one", 0);
		movieMenu.setSelectedIndex(0);
		frame.setIconImage(frameLogo.getImage());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 250);
		frame.setContentPane(panel);
		panel.setBackground(Color.blue);
		panel.add(movieMenu);
		panel.add(buttonPanel);
		movieMenu.setToolTipText("Movies are listed in alphabetical order");
		movieMenu.addActionListener(this);
		createFile.addActionListener(this);
		editFile.addActionListener(this);
		frame.setVisible(true);
		
		
		OpenVLC = new CommandPrompt();

		try {
			OpenVLC.RunSocketVLC();
		} catch (IOException e) {
			e.printStackTrace();
		}

		YTimer = new YeltsinScheduler();
		YTimer.StartTimer();
		
		System.out.println(YTimer.getCurrentTime());


	}
	
	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == movieMenu){
			Scanner in;
			ArrayList <Integer>movieTimeStart=new ArrayList<Integer>();
			ArrayList <Integer>movieTimeStop=new ArrayList<Integer>();
			ArrayList <String>movieAction=new ArrayList<String>();
			@SuppressWarnings({ "rawtypes" })
			JComboBox stuff= (JComboBox)e.getSource();
			name=(String) stuff.getSelectedItem();
			System.out.println(name+"\n");
			
					try {
						in=new Scanner(new File("Movies\\"+name+".csv"));
	
						while(in.hasNext()){
							
							in.useDelimiter(",");
							movieTimeStart.add(in.nextInt());
							movieTimeStop.add(in.nextInt());
							movieAction.add(in.next());
						}
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
					
					
			for(int i=0; i<movieTimeStart.size();i++){
				System.out.println(movieTimeStart.get(i));
				System.out.println(movieTimeStop.get(i));
				System.out.println(movieAction.get(i));
			}
						
			//Load movie parameters into Yeltsin Timer
			YTimer.SetMovieTimeStartList(movieTimeStart);
			YTimer.SetMovieTimeStopList(movieTimeStop);
			YTimer.SetMovieActionList(movieAction);
			
		}
					
		if(e.getSource() == createFile){		
			movieMenu.setSelectedIndex(0);
			fileEditor = new MovieFileEditor("FILE_CREATOR", name, YTimer);
			refreshList = fileEditor.syncSaveButton();
			refreshList.addActionListener(this);
		}
		
		if(e.getSource() == editFile){
			if(movieMenu.getSelectedIndex() != 0){
				fileEditor = new MovieFileEditor("FILE_EDITOR", name, YTimer);
			}
		}
		
		if(e.getSource() == refreshList){
			movieMenu.insertItemAt(fileEditor.getMovieTitle(), 1);
			movieMenu.setSelectedIndex(1);
		}	
	}
	
	
}
