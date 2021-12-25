import java.util.ArrayList;

public class DoubleShooter extends Enemy {

	public DoubleShooter(int xCord, int yCord) {
		super(xCord, yCord);
		//makes changes to point value
		setPointValue(40);
	}
	/**
	 * Overrides Ship's fire method
	 * returns an array with two projectiles 
	 */
	public ArrayList<Projectile> fire() {
		super.fire();
		ArrayList<Projectile> projectiles = new ArrayList<>();
		int xPosition = getX()+getWidth()/2;
		//adds two projectiles a quarter of the way from the top and bottom of the ship
		projectiles.add(new Projectile(xPosition,getY()+getHeight()/4,getFireDirection()));
		projectiles.add(new Projectile(xPosition,getY()+3*getHeight()/4,getFireDirection()));
		return projectiles;		
	}

}
