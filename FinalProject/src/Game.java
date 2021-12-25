import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
/*
 * Shawn Plotko
 * Jan 20, 2020
 * Space Fighter - Final Project
 * 
 * Resources:
 * https://docs.oracle.com/javase/8/docs/api/javax/swing/JTable.html
 * https://docs.oracle.com/javase/tutorial/uiswing/events/actionlistener.html
 * https://docs.oracle.com/javase/7/docs/api/java/util/Timer.html
 * https://alvinalexander.com/blog/post/jfc-swing/how-set-jframe-size-fill-entire-screen-maximize
 * https://docs.oracle.com/javase/7/docs/api/javax/swing/JOptionPane.html
 */
public class Game extends JFrame{
	public Game() {
		//sets height and width to approximate height and width of the screen
		int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		int width =(int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();;
		
		
		//sets JFrame's properties
		setTitle("Space Fighter");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(width, height);
		setFocusable(true);
		setLocationRelativeTo(null);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setResizable(false);
		setVisible(true);
		
		
		//Get actual frame size without any borders or title bar
		Dimension actualSize = getContentPane().getSize();
		//Creates board with these dimensions
		Board board = new Board(actualSize.width,actualSize.height);
		
		//adds the board to the jframe
 		add(board);
 		//adds the board as a keylistener
		addKeyListener(board);

		
	}
	public static void main(String[] args) throws IOException, InterruptedException {
		//creates startpage
		StartPage startPage = new StartPage();
		
		
	}

}
