import java.util.ArrayList;
import java.util.Random;

public class EnemySpawner {
	private final int QUAD_WIDTH = 1000;
	private int boardWidth, boardHeight;
	private int enemiesPerQuad;
	private Random r;
	private int waveCount;
	private int defaultShipRate, doubleShooterRate, heavyShipRate, speedShipRate;
	private ArrayList<Enemy> enemiesThisWave;

	public EnemySpawner(int boardWidth, int boardHeight) {
		this.boardWidth = boardWidth;
		this.boardHeight = boardHeight;
		enemiesThisWave = new ArrayList<Enemy>();
		r = new Random();
	}
	/**
	 * Sets the appropriate spawn rate (percent) for each ship based on the wave count
	 */
	private void setSpawnRates() {
		if (waveCount <= 1) {
			enemiesPerQuad = 3;
			defaultShipRate = 100;
		} else if (waveCount <= 10) {
			enemiesPerQuad = 3;
			defaultShipRate = 50;
			doubleShooterRate = 40;
			heavyShipRate = 5;
			speedShipRate = 5;
		} else if (waveCount <= 15) {
			enemiesPerQuad = 6;
			defaultShipRate = 25;
			doubleShooterRate = 50;
			heavyShipRate = 15;
			speedShipRate = 10;
		} else {
			enemiesPerQuad = 10;
			defaultShipRate = 7;
			doubleShooterRate = 31;
			heavyShipRate = 31;
			speedShipRate = 31;
		}
	}

	/**
	 * Takes enemy spawn coordinates
	 * Generates a random enemy based on spawn rates and returns that enemy
	 */
	private Enemy getNewEnemy(int x, int y) {
		//generates random number between 1 and 100
		int rndNum = r.nextInt(100) + 1;
		
		//returns appropriate ship based on number generated and each ships spawn rates
		if (rndNum <= defaultShipRate) {
			return new DefaultShip(x, y);
		} else if (rndNum <= doubleShooterRate + defaultShipRate) {
			return new DoubleShooter(x, y);
		} else if (rndNum <= heavyShipRate + doubleShooterRate + defaultShipRate) {
			return new HeavyShip(x, y);
		} else {
			return new SpeedShip(x, y);
		}
	}
	
	/**
	 * Gets quadrant number as parameter
	 * Adds appropriate number of enemies located within the specified quadrant to the wave array
	 */
	private void addQuad(int quadrant) {
		int rndX = 0, rndY = 0;
		boolean canSpawn;
		int i = 1;
		
		//loops until the defined number of enemies has been added
		while (i <= enemiesPerQuad) {
			//based on what quadrant was indicated
			switch (quadrant) {
			//set random x and y coordinate which are within the specified quadrant
			case 1:
				rndX = r.nextInt(QUAD_WIDTH + 1) + boardWidth + QUAD_WIDTH;
				rndY = r.nextInt(boardHeight / 2);
				break;
			case 2:
				rndX = r.nextInt(QUAD_WIDTH + 1) + boardWidth;
				rndY = r.nextInt(boardHeight / 2);
				break;
			case 3:
				rndX = r.nextInt(QUAD_WIDTH + 1) + boardWidth;
				rndY = r.nextInt(boardHeight / 2 - 200) + boardHeight / 2;
				break;
			case 4:
				rndX = r.nextInt(QUAD_WIDTH + 1) + boardWidth + QUAD_WIDTH;
				rndY = r.nextInt(boardHeight / 2) + boardHeight / 2;
				break;
			}
			//creates new enemy with generated coordinates
			Enemy newEnemy = getNewEnemy(rndX, rndY);
			canSpawn = true;
			//sets if enemy can spawn at the indicated coordinates
			//sets canSpawn to false if part of the enemy is off the bottom of the board
			if (newEnemy.getY() + newEnemy.getHeight() >= boardHeight) {
				canSpawn = false;
			} else {
				//loops through all other generated enemies for this wave and checks if they overlap with the recently created one
				for (int j = 0; j < enemiesThisWave.size(); j++) {
					//if they collide canSpawn is false
					if (newEnemy.collided(enemiesThisWave.get(j))) {
						canSpawn = false;
						break;
					}

				}
			}
			//If the ship can spawn enemy is added to the wave and number of enemies created for this quadrant is incremented
			if (canSpawn) {
				enemiesThisWave.add(newEnemy);
				i++;
			}
		}
	}
	/**
	 * Gets an array of enemies, creates a wave of enemies and adds them to this array, "spawning them"
	 */
	public void spawnWave(ArrayList<Enemy> enemies) {
		//increments wave count
		waveCount++;
		setSpawnRates();
		//adds all 4 quadrants to the wave
		for (int i = 1; i <= 4; i++) {
			addQuad(i);
		}
		//adds wave to enemy array
		enemies.addAll(enemiesThisWave);
		//clears the wave array
		enemiesThisWave.clear();

	}
	/**
	 * returns waveCount
	 */
	public int getWaveCount() {
		return waveCount;
	}
}
