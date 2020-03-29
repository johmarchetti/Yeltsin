import java.applet.Applet;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class yeltsin extends Applet implements ActionListener{

	private static final long serialVersionUID = 1L;

	JPanel panel=new JPanel();
	JFrame frame=new JFrame("Yeltsin - Movie Editor");
	ImageIcon frameLogo = new ImageIcon("YeltsinLogo.png");             //image is currently blank
	File movieFiles[];
	CommandPrompt OpenVLC;
	YeltsinScheduler YTimer;
	
	public void init(){
		
		File movieDirectory = new File("Movies");
		movieFiles = movieDirectory.listFiles();
		String movies[] = new String[movieFiles.length];
		for(int i = 0; i < movieFiles.length; i++){
			movies[i] = movieFiles[i].getName();
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		JComboBox movieMenu=new JComboBox(movies);
		frame.setIconImage(frameLogo.getImage());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 250);
		frame.setContentPane(panel);
		panel.setBackground(Color.blue);
		panel.add(movieMenu);
		movieMenu.setToolTipText("Movies are listed in alphabetical order");
		movieMenu.addActionListener(this);
		frame.setVisible(true);
		
		
		OpenVLC = new CommandPrompt();

		try {
			OpenVLC.RunSocketVLC();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		YTimer = new YeltsinScheduler();
		YTimer.StartTimer();

	}
	public void actionPerformed(ActionEvent e) {
		Scanner in;
		ArrayList <Integer>movieTimeStart=new ArrayList<Integer>();
		ArrayList <Integer>movieTimeStop=new ArrayList<Integer>();
		ArrayList <String>movieAction=new ArrayList<String>();
		@SuppressWarnings({ "rawtypes" })
		JComboBox stuff= (JComboBox)e.getSource();
		String name=(String) stuff.getSelectedItem();
		System.out.println(name+"\n");
		
				try {
					in=new Scanner(new File("Movies\\"+name));//+".csv"

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
	
	
}
