
public class Projectile extends Sprite {
	private int damage;
	
	public Projectile(int xCord, int yCord,int xDirection) {
		super(xCord, yCord);
		setXSpeed(10);
		setXDirection(xDirection);
		setImage("bullet2.png");
		damage =1;
	}
	/**
	 * returns the bullet's damage
	 */
	public int getDamage() {
		return damage;
	}

}
