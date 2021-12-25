import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class StartPage extends JFrame  {
	
	
	public StartPage() throws IOException {
		//Sets background image as a JLable
		String path = "StartPageBackground.png";
	    File file = new File(path);
	    Font btnFont = new Font("Dialog",1,20);
	    BufferedImage image = ImageIO.read(getClass().getResourceAsStream(path));
	    JLabel label = new JLabel(new ImageIcon(image));
	    setTitle("Space Fighters");
	    //Creates play button and sets appropriate size and coordinates
	    JButton playBtn = new JButton("New Game");
	    playBtn.setFont(btnFont);
	    playBtn.setBounds(100, 300, 150, 80);
	    
	    //Creates statistics button and sets appropriate size and coordinates
	    JButton leaderboardBtn = new JButton("High Scores");
	    leaderboardBtn.setFont(btnFont);
	    leaderboardBtn.setBounds(300, 300, 150, 80);
		
		//Creates help button and sets appropriate size and coordinates
		JButton helpBtn = new JButton("Help");
		helpBtn.setFont(btnFont);
		helpBtn.setBounds(500, 300, 150, 80);
		
		//Creates a JPanel
		JPanel panel = new JPanel();
		
		//Adds all the buttons and background image and puts them in the appropriate container so that they are displayed
		panel.add(playBtn);
		panel.add(leaderboardBtn);
		panel.add(helpBtn);
		getContentPane().add(helpBtn);
		getContentPane().add(leaderboardBtn);
		getContentPane().add(playBtn);
		getContentPane().add(label);
		//Exits JFrame if top right x is clicked
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		//Makes JFrame appear in the middle of the screen
		setLocationRelativeTo(null);
		//Makes JFrame visible
		setVisible(true);
		
		
		//Handles play button clicks
		playBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//creates a new game
				Game game = new Game();
				
			}
			
		});
		//Handles leaderboard button clicks
		leaderboardBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//creates a new leaderboard	
				LeaderBoard leaderboard = new LeaderBoard();
				
			}
			
		});
		//Handles help button clicks
		helpBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
					//shows the help screen
					showHelpScreen();
				
			}
			
		});
	}
	
	
	/**
	 * Opens pdf file with instructions
	 */
	private void showHelpScreen() {
		File file = new File("Instructions.pdf");
		try {
			Desktop.getDesktop().open(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
}
