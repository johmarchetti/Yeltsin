import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class MovieFileEditor extends yeltsin {
	/**
	 * editorType should either be "FILE_CREATOR" or "FILE_EDITOR"
	 */
	public MovieFileEditor(String editorType, String movieName, YeltsinScheduler yeltSched){
		
		yTimer = yeltSched;
		
		if(editorType.equals("FILE_CREATOR")){
			frame = new JFrame("Movie File Creator - TITLE");
			frame.setSize(300, 100);frame.add(panel);
			
			panel.add(enterName);
			panel.add(creatorEnterButton);
			
			creatorEnterButton.addActionListener(this);
			frame.add(panel);
			frame.setIconImage(frameLogo.getImage());
			panel.setBackground(Color.orange);
			frame.setVisible(true);
			
		}
		if(editorType.equals("FILE_EDITOR")){
			movieTitle = movieName;
			createMovieFileEditorWindow();	
		}
		
	}
	
	private void createMovieFileEditorWindow(){
		file = new File("Movies\\"+movieTitle+".csv");
		
		frame = new JFrame(movieTitle+" File Editor");
		frame.setSize(600, 500);
		frame.add(panel);
		frame.setIconImage(frameLogo.getImage());
		
		panel.remove(enterName);
		panel.remove(creatorEnterButton);
		panel.setBackground(Color.orange);
		
		JPanel currentTimePanel = new JPanel();
		currentTimePanel.setBackground(Color.orange);
		currentTimePanel.add(getCurrentTime);
		currentTimeLabel.setColumns(10);
		currentTimePanel.add(currentTimeLabel);
		
		panel.add(currentTimePanel);
		panelOfActions.setLayout(GridOfActions);
		panelOfActions.add(new JLabel(" Time to Begin Action "));
		panelOfActions.add(new JLabel(" Time to End Action "));
		panelOfActions.add(new JLabel(" Action to Take (mute or skip) "));
		panel.add(panelOfActions);
		
		JPanel bottomButtonPanel = new JPanel();
		bottomButtonPanel.setBackground(Color.orange);
		bottomButtonPanel.add(addAction);
		bottomButtonPanel.add(save);
		panel.add(bottomButtonPanel);
		
		getCurrentTime.addActionListener(this);
		addAction.addActionListener(this);
		save.addActionListener(this);
		
		frame.setVisible(true);
		
		fillAreaOfActions();
	}
	
	public void actionPerformed(ActionEvent e){
		
		if(e.getSource() == creatorEnterButton){
			frame.setVisible(false);
			movieTitle = enterName.getText();
			System.out.println(movieTitle);
			createMovieFileEditorWindow();
		}
		if(e.getSource() == save){
			int answer = JOptionPane.showConfirmDialog(frame, "Do you want to save your changes?", "Save?", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
			if(answer == JOptionPane.YES_OPTION){
				try {
					if(beginTimes.size() > 0){
						saveFileData();
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				frame.setVisible(false);
			}
			else if(answer == JOptionPane.NO_OPTION){
				frame.setVisible(false);
			}
		}
		if(e.getSource() == addAction){
			beginTimes.add(new JTextField(""));
			endTimes.add(new JTextField(""));
			actions.add(new JTextField(""));
			
			GridOfActions.setRows(beginTimes.size()+1);
			
			panelOfActions.add(beginTimes.get(beginTimes.size()-1));
			panelOfActions.add(endTimes.get(endTimes.size()-1));
			panelOfActions.add(actions.get(actions.size()-1));
			frame.setVisible(true);
		
		}
		if(e.getSource() == getCurrentTime){
			currentTime = -1;
			for(int j = 0; j < 7; j++){
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {	e1.printStackTrace(); }
				if(yTimer.getCurrentTime() != -1)
					currentTime = yTimer.getCurrentTime();
			}
			currentTimeLabel.setText(String.valueOf(currentTime));
		}
	}
	
	private void fillAreaOfActions(){
		Scanner in = null;
		try {
			in=new Scanner(file);

			while(in.hasNext()){
				
				in.useDelimiter(",");
				beginTimes.add(new JTextField(String.valueOf(in.nextInt())));
				endTimes.add(new JTextField(String.valueOf(in.nextInt())));
				actions.add(new JTextField(in.next()));

			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		in.close();
		
		GridOfActions.setRows(beginTimes.size()+1);

		for(int i = 0; i < beginTimes.size(); i++){
			panelOfActions.add(beginTimes.get(i));
			panelOfActions.add(endTimes.get(i));
			panelOfActions.add(actions.get(i));
		}
		
	}
	
	private void saveFileData() throws IOException{
		FileWriter writer = new FileWriter(file);
		PrintWriter printer = new PrintWriter(writer);
		
		for(int i = 0; i < beginTimes.size(); i++){
			printer.print(beginTimes.get(i).getText()+",");
			printer.print(endTimes.get(i).getText()+",");
			printer.print(actions.get(i).getText()+",");
		}
		
		writer.close();
		printer.close();
	}
	
	public JButton syncSaveButton(){
		return save;
	}
	
	public String getMovieTitle(){
		return movieTitle;
	}
	
	private static final long serialVersionUID = 1L;

	private String movieTitle = "";
	private int currentTime;
	private YeltsinScheduler yTimer;
	private File file;
	
	private JPanel panel = new JPanel();
	private JButton creatorEnterButton = new JButton("Enter");
	private JTextField enterName = new JTextField("Type Movie Title Here");
	private JFrame frame;
	private ImageIcon frameLogo = new ImageIcon("YeltsinLogo.png");
	private JButton addAction = new JButton("Add Action");
	private JButton save = new JButton("Save and Exit");
	private JButton getCurrentTime= new JButton("Get Current Time (Seconds)");
	private JTextField currentTimeLabel = new JTextField(" current time ");
	private ArrayList <JTextField>beginTimes = new ArrayList<JTextField>();
	private ArrayList <JTextField>endTimes = new ArrayList<JTextField>();
	private ArrayList <JTextField>actions = new ArrayList<JTextField>();
	private GridLayout GridOfActions = new GridLayout(1,3);
	private JPanel panelOfActions = new JPanel();
}
