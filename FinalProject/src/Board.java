
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import javax.swing.Timer;

public class Board extends JPanel implements KeyListener, ActionListener {
	private int width, height;
	private Player playerShip;
	private ArrayList<Projectile> playerBullets;
	private ArrayList<Projectile> enemyBullets;
	private ArrayList<Enemy> enemies;
	private Timer timer;
	private EnemySpawner spawner;
	private Image bckgrndImage;
	private int playerScore;
	private JLabel scoreLbl, healthLbl, waveCountLbl;

	public Board(int boardWidth, int boardHeight) {
		//sets size of the board
		width = boardWidth;
		height = boardHeight;
		setSize(width, height);
		
		Font font = new Font("Verdana", Font.BOLD, 20);
		setFocusable(true);
		
		//Initializes game components
		initializeGame();
		
		//creates and adds labels
		scoreLbl = new JLabel();
		healthLbl = new JLabel();
		waveCountLbl = new JLabel();
		scoreLbl.setFont(font);
		healthLbl.setFont(font);
		waveCountLbl.setFont(font);
		scoreLbl.setForeground(Color.WHITE);
		healthLbl.setForeground(Color.WHITE);
		waveCountLbl.setForeground(Color.WHITE);
		add(scoreLbl);
		add(waveCountLbl);
		add(healthLbl);
		
		//sets background image
		try {
			bckgrndImage = ImageIO.read(getClass().getResourceAsStream("spacebackground.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		addKeyListener(this);
		
		//starts timer that activates actionlistner
		timer.start();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		redraw(g);
	}
	
	/**
	 * Initializes major fields (those that must be reset at the start of every game
	 */
	private void initializeGame() {
		spawner = new EnemySpawner(width, height);
		playerShip = new Player(0, height / 2);
		playerScore = 0;
		timer = new Timer(10, this);
		playerBullets = new ArrayList<>();
		enemyBullets = new ArrayList<>();
		enemies = new ArrayList<>();

	}
	/**
	 * Updates each board label so that they display the appropriate values
	 */
	private void updateLabels() {
		scoreLbl.setText("Score: " + playerScore + "   ");
		waveCountLbl.setText("Wave: " + spawner.getWaveCount() + "   ");
		healthLbl.setText("Health: " + playerShip.getHealth());

	}
	
	/**
	 * redraws all game images using java.awt.Graphics
	 */
	private void redraw(Graphics g) {
		//redraws background
		g.drawImage(bckgrndImage, 0, 0, width, height, null);
		
		//redraws all player projectiles
		for (Projectile bullets : playerBullets) {
			g.drawImage(bullets.getImage(), bullets.getX(), bullets.getY(), bullets.getWidth(), bullets.getHeight(),
					null);
		}
		//redraws enemy projectiles
		for (Projectile bullets : enemyBullets) {
			g.drawImage(bullets.getImage(), bullets.getX(), bullets.getY(), bullets.getWidth(), bullets.getHeight(),
					null);
		}
		//redraws enemies
		for (Ship enemy : enemies) {
			g.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight(), null);
			// System.out.println("redraw");
		}
		//redraws the player
		g.drawImage(playerShip.getImage(), playerShip.getX(), playerShip.getY(), playerShip.getWidth(),
				playerShip.getHeight(), null);

	}

	@Override
	/**
	 * Gets the key pressed
	 * Sets the appropriate direction for arrow keys and fires if space is pressed for playership
	 */
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_LEFT) {
			playerShip.setXDirection(-1);
		} else if (key == KeyEvent.VK_RIGHT) {
			playerShip.setXDirection(1);
		} else if (key == KeyEvent.VK_UP) {
			playerShip.setYDirection(-1);
		} else if (key == KeyEvent.VK_DOWN) {
			playerShip.setYDirection(1);
		} else if (key == KeyEvent.VK_SPACE && playerShip.canFire()) {
			playerBullets.addAll(playerShip.fire());
		}
	}

	@Override
	/**
	 * Sets playership direction to 0 once a key is released
	 * playership stops moving
	 */
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_LEFT || key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
			playerShip.setXDirection(0);
			playerShip.setYDirection(0);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	/**
	 * updates all game components and repaints the graphics
	 * checks if game has ended
	 */
	public void actionPerformed(ActionEvent arg0) {
		enemyUpdate();
		projectileUpdate();
		playerUpdate();
		updateLabels();
		repaint();
		
		//if player health is 0 or less, end of game messages appear
		if (playerShip.getHealth() <= 0) {
			try {
				//gets the user's name and saves it
				saveStats(getUsersName());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//asks user if they would like to play again
			switch (endOfGameMessage()) {
			//play again is press
			case 0:
				//resets major game objects, starts game again
				initializeGame();
				break;
			//exit is pressed
			case 1:
				//terminates program
				System.exit(0);
			}

		}

	}
	/**
	 * Gets users name as a parameter
	 * Writes name, score and wave count to a text file
	 */
	private void saveStats(String usersName) throws IOException {
		String path = "stats.txt";
		Writer writer = new FileWriter(path, true);
		PrintWriter output = null;
		output = new PrintWriter(writer);
		if (usersName != null) {
			output.println(usersName + "," + playerScore + "," + spawner.getWaveCount());
		}
		output.close();
	}
	/**
	 * Uses JOptionPane and text field to get the users name
	 * Returns the users name as a string
	 */
	private String getUsersName() {
		final int CANCEL_OPTION = 1;
		Object[] options = { "Save", "Don't Save" };

		JPanel panel = new JPanel();
		panel.add(new JLabel("Game Over! Enter your name to save:"));
		JTextField textField = new JTextField(10);
		panel.add(textField);
		
		//stores if user chose to save or not as a integer
		int result = JOptionPane.showOptionDialog(null, panel, "Game Over", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, null);
		//returns null if users chose to not save
		if (result == CANCEL_OPTION) {
			return null;
		}
		String usersName = textField.getText();
		return usersName;
	}
	/**
	 * Updates all on screen enemies
	 */
	private void enemyUpdate() {
		//spawns new wave of enemies if no more are remaining
		if (enemies.isEmpty()) {
			spawner.spawnWave(enemies);
		}
		//loops through all on screen enemies
		for (int i = 0; i < enemies.size(); i++) {
			Enemy currentEnemy = enemies.get(i);
			//decreases shooting cooldown time
			currentEnemy.cooldownDecrease(timer.getDelay());
			
			//if the enemy has been killed and is exploding 
			if (currentEnemy.isExploding()) {
				//removes enemy and increments player score by the killed enemies point value
				enemies.remove(currentEnemy);
				playerScore += currentEnemy.getPointValue();
				//avoids skipping an enemy due to element removal 
				i--;
			}
			//if the enemy has reached the left side of the screen and is no longer visible
			if(currentEnemy.getX() + currentEnemy.getWidth() < 0) {
				//removes enemy 
				enemies.remove(currentEnemy);
				//decreases player score by the killed enemies point value but doesn't allow score to go into negatives
				if(playerScore - currentEnemy.getPointValue()>=0) {
					playerScore -= currentEnemy.getPointValue();
				}else {
					playerScore = 0;
				}
				
				//avoids skipping an enemy due to element removal 
				i--;
			}
			//if the enemy has no health, enemy explodes
			if (currentEnemy.getHealth() <= 0) {
				currentEnemy.explode();
			}
			
			//if the enemy can fire enemy fires and bullets fired are added to enemy bullet array
			if (currentEnemy.canFire(width)) {
				enemyBullets.addAll(currentEnemy.fire());
			}
			
			//enemy moves
			currentEnemy.move();

		}
	}
	/**
	 * Updates all on screen projectiles
	 */
	private void projectileUpdate() {
		//loops through all on screen player bullets
		for (int i = 0; i < playerBullets.size(); i++) {
			Projectile currentBullet = playerBullets.get(i);
			
			//removes bullet if it has gone off screen
			if (currentBullet.getX() > getWidth()) {
				playerBullets.remove(i);
			} else {
				//moves the bullet
				currentBullet.move();
				//checks if bullet has collided with any enemies
				for (Ship enemy : enemies) {
					//if a bullet has collided with an enemy the enemy takes the appropriate damage and the bullet is removed
					if (currentBullet.collided(enemy)) {
						enemy.takeDamage(currentBullet.getDamage());
						playerBullets.remove(i);
						break;
					}
				}
			}

		}
		//loops through all on screen enemy bullets
		for (int i = 0; i < enemyBullets.size(); i++) {
			Projectile currentBullet = enemyBullets.get(i);
			//removes bullet if it has gone off screen
			if (currentBullet.getX() < 0) {
				enemyBullets.remove(i);
			} else {
				//otherwise bullet is moved
				currentBullet.move();
				//checks if bullet collided with the player, if it has player takes appropriate damage and bullet is removed
				if (currentBullet.collided(playerShip)) {
					playerShip.takeDamage(currentBullet.getDamage());
					enemyBullets.remove(i);
				}
			}
			
			
		}
	}
	/**
	 * Updates the player
	 */
	private void playerUpdate() {
		//decreases the players firing cooldown time
		playerShip.cooldownDecrease(timer.getDelay());
		//moves the player if they can be moved (are not out of bounds)
		if (canPlayerMove()) {
			playerShip.move();
		}

	}
	/**
	 * Checks if the user can move the player in the requested direction
	 * returns boolean indicating if playership can be moved
	 */
	private boolean canPlayerMove() {
		//gets ships midpoint coordinates
		int midX = playerShip.getX() + playerShip.getWidth() / 2;
		int midY = playerShip.getY() + playerShip.getHeight() / 2;
		boolean canMove = false;
		
		
		switch (playerShip.getXDirection()) {
		case -1:
			canMove = midX - playerShip.getXSpeed() > 0;
			break;
		case 1:
			canMove = midX + playerShip.getXSpeed() < width;
			break;
		}
		if (!canMove) playerShip.setXDirection(0);
		switch (playerShip.getYDirection()) {
		case -1:
			canMove = midY - playerShip.getYSpeed() > 0;
			break;
		case 1:
			canMove = midY + playerShip.getYSpeed() < height;
			break;
		}
		if (!canMove) playerShip.setYDirection(0);
		

		return canMove;
	}
	/**
	 * Informs user that the game has ended and prompts them to choose to either play again or quit
	 * returns users choice as an integer
	 */
	private int endOfGameMessage() {
		Object[] options = { "Play again", "Quit" };
		int btnPressed = JOptionPane.showOptionDialog(null, "Would you like to play again?", "Game Over",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);

		return btnPressed;
	}

}