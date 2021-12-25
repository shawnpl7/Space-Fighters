import java.util.Random;

public abstract class Enemy extends Ship {
	private static final int DEFAULT_FIRE_DIRECTION = -1;
	private static final int DEFAULT_HEALTH = 1;
	private static final int MAX_COOLDOWN_TIME = 8120;
	private final String DEFAULT_IMAGE_PATH = getClass().getName()+".png";
	private int pointValue;
	
	public Enemy(int xCord, int yCord) {
		super(xCord, yCord,DEFAULT_FIRE_DIRECTION,DEFAULT_HEALTH,MAX_COOLDOWN_TIME);
		setImage(DEFAULT_IMAGE_PATH);
		setXDirection(DEFAULT_FIRE_DIRECTION);
		setXSpeed(1);
	}
	/**
	 * takes an integer parameter, sets enemy's point value
	 */
	public void setPointValue(int value) {
		pointValue = value;
	}
	/**
	 * returns enemy's point value
	 */
	public int getPointValue() {
		return pointValue;
	}
	/**
	 * gets the width of the board it's in
	 * returns if it is able to fire
	 */
	public boolean canFire(int width) {
		return getX()+getWidth()<width&&super.canFire();
		
	}
	
	
	
}
