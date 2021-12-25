
public class Player extends Ship{
	private static final int DEFAULT_FIRE_DIRECTION = 1;
	private static final int DEFAULT_HEALTH = 3;
	private static final int MAX_COOLDOWN_TIME = 520;
	private static final String DEFAULT_IMAGE_PATH = "playership.png";
	public Player(int xCord, int yCord) {
		super(xCord, yCord, DEFAULT_FIRE_DIRECTION,DEFAULT_HEALTH,MAX_COOLDOWN_TIME);
		setImage(DEFAULT_IMAGE_PATH);
		setXSpeed(2);
		setYSpeed(2);
	}
	

	

	
}
