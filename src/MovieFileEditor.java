import java.applet.Applet;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class MovieFileEditor extends Applet {
	/**
	 * editorType should either be "FILE_CREATOR" or "FILE_EDITOR"
	 */
	public MovieFileEditor(String editorType){
		if(editorType.equals("FILE_CREATOR")){
			frame = new JFrame("Movie File Creator");
		}
		if(editorType.equals("FILE_EDITOR")){
			frame = new JFrame("Movie File Editor");
		}
		
		frame.setSize(500, 400);
		frame.add(panel);
		frame.setIconImage(frameLogo.getImage());
		panel.setBackground(Color.orange);
		frame.setVisible(true);
	}
	private static final long serialVersionUID = 1L;

	
	private JPanel panel = new JPanel();
	private JFrame frame;
	private ImageIcon frameLogo = new ImageIcon("YeltsinLogo.png");
}
